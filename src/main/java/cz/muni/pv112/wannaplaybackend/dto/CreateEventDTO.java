package cz.muni.pv112.wannaplaybackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime dateTime;
    private Integer capacity;
    private Long partyId;
    private String name;
    private String description;
}
