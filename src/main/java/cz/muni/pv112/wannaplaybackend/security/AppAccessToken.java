package cz.muni.pv112.wannaplaybackend.security;

import lombok.Data;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
public class AppAccessToken {
    private String access_token;
    private String token_type;
}
