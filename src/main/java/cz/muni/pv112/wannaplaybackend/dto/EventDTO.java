package cz.muni.pv112.wannaplaybackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime dateTime;
    private EventOwnerDTO owner;
    private EventPartyDTO party;
    private List<EventParticipantDTO> participants;
}
