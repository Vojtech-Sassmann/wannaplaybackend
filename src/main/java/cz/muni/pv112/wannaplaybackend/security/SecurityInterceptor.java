package cz.muni.pv112.wannaplaybackend.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import cz.muni.pv112.wannaplaybackend.config.WannaplayProperties;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    public static final String PRINCIPAL_ATTR = "Principal";

    private static final String EXT_SOURCE_HEADER = "WP-ExtSource";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String FACEBOOK_EXT_SOURCE = "facebook";
    private static final String GOOGLE_EXT_SOURCE = "google";

    private final WannaplayProperties wannaplayProperties;
    private final UserRepository userRepository;

    public SecurityInterceptor(WannaplayProperties wannaplayProperties, UserRepository userRepository) {
        this.wannaplayProperties = wannaplayProperties;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String extSource = getUserExtSource(request, response);
        if (extSource == null) {
            return false;
        }

        String extSourceUserId = getUserExtSourceId(request, response, extSource);
        if (extSourceUserId == null) {
            return false;
        }

        Optional<User> principalUser = userRepository.findByExternalIdentity(extSourceUserId, extSource);

        if (!principalUser.isPresent() && !isCallingCreateUserMethod(request)) {
            log.warn("Method called with a not existing user, id: {}", extSourceUserId);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Method called with a not existing user.");
            return false;
        }

        setPrincipal(request, principalUser.orElse(null), extSource, extSourceUserId);

        return true;
    }

    private void setPrincipal(HttpServletRequest request, User principalUser, String extSource, String extSourceUserId) {
        Principal principal = Principal.builder()
                .id(principalUser != null ? principalUser.getId() : null)
                .externalId(extSourceUserId)
                .externalSource(extSource)
                .build();

        request.setAttribute(PRINCIPAL_ATTR, principal);
    }

    private boolean isCallingCreateUserMethod(HttpServletRequest request) {
        return request.getRequestURI().endsWith("/user") && "PUT".equals(request.getMethod());
    }

    private String getUserExtSource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String extSource = request.getHeader(EXT_SOURCE_HEADER);
        if (extSource == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing 'WP-ExtSource' header");
            return null;
        }

        return extSource;
    }

    private String getUserExtSourceId(HttpServletRequest request, HttpServletResponse response, String extSource)
            throws GeneralSecurityException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader == null) {
            log.warn("No authorization header is present.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        String inputToken = authHeader.replace("Bearer ", "");

        if (FACEBOOK_EXT_SOURCE.equals(extSource)) {
            return handleFacebookAuth(inputToken);
        } else if (GOOGLE_EXT_SOURCE.equals(extSource)) {
            return handleGoogleAuth(inputToken);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid extSource.");
            return null;
        }
    }

    private String handleGoogleAuth(String httpToken) throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = verifyGoogleToken(httpToken);

        if (payload == null) {
            log.error("Google auth failure.");
            return null;
        }

        return payload.getSubject();
    }

    private String handleFacebookAuth(String httpToken) {
        FacebookToken token = verifyFacebookToken(httpToken);

        if (token == null) {
            log.error("Facebook auth failure.");
            return null;
        }
        return token.getData().getUserId();
    }

    private GoogleIdToken.Payload verifyGoogleToken(String httpToken) throws GeneralSecurityException, IOException {
        String clientId = wannaplayProperties.getGoogleClientId();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken token = verifier.verify(httpToken);
        if (token == null) {
            log.error("invalid google token");
            return null;
        }
        GoogleIdToken.Payload payload = token.getPayload();

        String userId = payload.getSubject();
        log.info("Google user id: {}", userId);

        return payload;
    }

    private FacebookToken verifyFacebookToken(String httpToken) {
        AppAccessToken appAccessToken = getFacebookAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        FacebookToken data;
        try {
            ResponseEntity<FacebookToken> res = restTemplate.getForEntity(
                    "https://graph.facebook.com/debug_token?input_token=" + httpToken +
                    "&access_token=" + appAccessToken.getAccess_token(), FacebookToken.class);
            data = res.getBody();
            if (data == null || data.getData() == null || !data.getData().isValid()) {
                log.error("Failed to receive app access token from facebook");
                return null;
            }
        } catch (RestClientException e) {
            log.error("Failed to receive app access token from facebook");
            return null;
        }

        return data;
    }

    private AppAccessToken getFacebookAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        String clientId = this.wannaplayProperties.getFacebookClientId();
        String clientSecret = this.wannaplayProperties.getFacebookClientSecret();
        ResponseEntity<AppAccessToken> res = restTemplate.getForEntity(
                "https://graph.facebook.com/oauth/access_token?" +
                "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=client_credentials", AppAccessToken.class);
        return res.getBody();
    }
}
