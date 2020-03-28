package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;

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

    /**
     * Adds the user of the current principal to the specified event.
     *
     * @param id event id
     * @param principal current principal
     */
    void joinEvent(Long id, Principal principal);
}
