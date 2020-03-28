package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPartyDTO {
    private Long id;
    private String name;
}
