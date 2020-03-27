package cz.muni.pv112.wannaplaybackend.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
public class DataResponse {
    @JsonProperty("data")
    private AuthorizationTokenData data;
}
