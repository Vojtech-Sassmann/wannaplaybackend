package cz.muni.pv112.wannaplaybackend.security;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface EventService {

    /**
     * Creates event from given information. The principal is added as one of the
     * participants.
     *
     * @param createEventDTO information needed for creation
     * @return id of the new event
     */
    Long createEvent(CreateEventDTO createEventDTO, Principal principal);

    /**
     * Returns all events from user parties which are happening in the future.
     * The events has to be for one of the principal parties.
     *
     * @return all events from user parties which are happening in the future.
     */
    List<EventDTO> getAllFutureEvents(Principal principal);
}
