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
     * The events has to be for one of the user parties.
     *
     * @param userId id of user whose events should be found
     * @return all events from user parties which are happening in the future.
     */
    List<EventDTO> getAllFutureEvents(Long userId);

    /**
     * Adds the user of the current principal to the specified event.
     *
     * @param id event id
     * @param principal current principal
     */
    void joinEvent(Long id, Principal principal);

    /**
     * Leaves events with the given id.
     *
     * @param id event id
     * @param principal current principal
     */
    void leaveEvent(Long id, Principal principal);

    /**
     * Finds event by its id.
     *
     * @param id id of searched event
     * @return found event
     */
    EventDTO findById(Long id);

    /**
     * Find events which are attended by the given user.
     *
     * @param userId user id
     * @return events which are attended by the given user
     */
    List<EventDTO> getUserFutureEvents(Long userId);
}
