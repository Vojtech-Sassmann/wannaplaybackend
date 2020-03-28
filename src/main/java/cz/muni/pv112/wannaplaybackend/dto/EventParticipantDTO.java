package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventParticipantDTO {
    private Long id;
    private String nick;
}
