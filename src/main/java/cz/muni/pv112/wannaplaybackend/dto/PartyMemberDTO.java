package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyMemberDTO {
    private Long id;
    private String nick;
}
