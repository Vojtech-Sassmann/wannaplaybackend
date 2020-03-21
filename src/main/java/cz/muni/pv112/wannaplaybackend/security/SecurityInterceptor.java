package cz.muni.pv112.wannaplaybackend.security;

import cz.muni.pv112.wannaplaybackend.config.WannaplayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    private final WannaplayProperties wannaplayProperties;

    public SecurityInterceptor(WannaplayProperties wannaplayProperties) {
        this.wannaplayProperties = wannaplayProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String inputToken = request.getHeader("Authorization").replace("Bearer ", "");
        AppAccessToken appAccessToken = getAccessToken();

        // TODO add google authorization

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<DataResponse> res = restTemplate.getForEntity(
                    "https://graph.facebook.com/debug_token?input_token=" + inputToken +
                    "&access_token=" + appAccessToken.getAccess_token(), DataResponse.class);
            DataResponse data = res.getBody();
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
