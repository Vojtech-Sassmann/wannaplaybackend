package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartyDTO {
    private long id;
    private String name;
}
