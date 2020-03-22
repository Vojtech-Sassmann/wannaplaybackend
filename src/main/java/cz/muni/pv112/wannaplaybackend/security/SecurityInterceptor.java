package cz.muni.pv112.wannaplaybackend.security;

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
import java.util.Optional;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    public static final String PRINCIPAL_ATTR = "Principal";

    private final WannaplayProperties wannaplayProperties;
    private final UserRepository userRepository;

    public SecurityInterceptor(WannaplayProperties wannaplayProperties, UserRepository userRepository) {
        this.wannaplayProperties = wannaplayProperties;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        String extSource = "facebook";

        if (authHeader == null) {
            log.warn("No authorization header is present.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        String inputToken = authHeader.replace("Bearer ", "");
        AppAccessToken appAccessToken = getAccessToken();

        // TODO add google authorization

        RestTemplate restTemplate = new RestTemplate();
        DataResponse data;
        try {
            ResponseEntity<DataResponse> res = restTemplate.getForEntity(
                    "https://graph.facebook.com/debug_token?input_token=" + inputToken +
                    "&access_token=" + appAccessToken.getAccess_token(), DataResponse.class);
            data = res.getBody();
            if (data == null || data.getData() == null || !data.getData().isValid()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                log.warn("Unauthorized");
                return false;
            }
        } catch (RestClientException e) {
            log.warn("Unauthorized: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        }


        Optional<User> principalUser =
                userRepository.findByExternalIdentity(data.getData().getUserId(), extSource);
        if (!principalUser.isPresent() &&
                !("/user".equals(request.getRequestURI()) && "PUT".equals(request.getMethod()))) {
            log.warn("Method called with a not existing user, id: {}", data.getData().getUserId());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Method called with a not existing user.");
            return false;
        }

        Principal principal = Principal.builder()
                .id(principalUser.map(User::getId).orElse(null))
                .externalId(data.getData().getUserId())
                .externalSource(extSource)
                .build();

        request.setAttribute(PRINCIPAL_ATTR, principal);

        return true;
    }

    private AppAccessToken getAccessToken() {
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
