package cz.muni.pv112.wannaplaybackend.dto;

import lombok.Data;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
public class CreateUserDTO {
    private String nick;
    private String externalSource;
    private String externalId;
}
