package cz.muni.pv112.wannaplaybackend.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
public class AuthorizationTokenData {
    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("is_valid")
    private boolean valid;

    @JsonProperty("user_id")
    private Long userId;
}
