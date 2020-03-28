package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDTO {

    private LocalDateTime dateTime;
    private Integer capacity;
    private Long partyId;
    private String name;
    private String description;
}
