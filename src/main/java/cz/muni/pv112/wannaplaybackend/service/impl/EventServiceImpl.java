package cz.muni.pv112.wannaplaybackend.service.impl;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.dto.Mappers;
import cz.muni.pv112.wannaplaybackend.models.Event;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.EventRepository;
import cz.muni.pv112.wannaplaybackend.repository.PartyRepository;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.EventService;
import cz.muni.pv112.wannaplaybackend.service.exceptions.EventFullException;
import cz.muni.pv112.wannaplaybackend.service.exceptions.EventNotExistsException;
import cz.muni.pv112.wannaplaybackend.service.exceptions.PartyNotExistsException;
import cz.muni.pv112.wannaplaybackend.service.exceptions.UserNotExistsException;
import cz.muni.pv112.wannaplaybackend.service.exceptions.UserNotMemberOfPartyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                            PartyRepository partyRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.partyRepository = partyRepository;
    }

    @Override
    public Long createEvent(CreateEventDTO createEventDTO, Principal principal) {
        Optional<User> owner = userRepository.findById(principal.getId());

        if (!owner.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        Optional<Party> party = partyRepository.findById(createEventDTO.getPartyId());

        if (!party.isPresent()) {
            throw new PartyNotExistsException("Party with given id does not exist.");
        }

        Event event = Event.builder()
                .dateTime(createEventDTO.getDateTime())
                .capacity(createEventDTO.getCapacity())
                .name(createEventDTO.getName())
                .owner(owner.get())
                .party(party.get())
                .description(createEventDTO.getDescription())
                .build();

        event.addParticipant(owner.get());

        event = eventRepository.save(event);

        return event.getId();
    }

    @Override
    public List<EventDTO> getAllFutureEvents(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        return eventRepository.findByDateTimeAfterAndPartyIn(ZonedDateTime.now(), user.get().getParties()).stream()
                .map(Mappers::mapEvent)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDTO> getUserFutureEvents(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        return eventRepository.findByDateTimeAfterAndParticipantsContains(ZonedDateTime.now(), user.get()).stream()
                .map(Mappers::mapEvent)
                .collect(Collectors.toList());
    }

    @Override
    public void joinEvent(Long id, Principal principal) {
        Optional<User> user = userRepository.findById(principal.getId());

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        Optional<Event> event = eventRepository.findById(id);

        if (!event.isPresent()) {
            throw new EventNotExistsException("Event with given id does not exist.");
        }

        if (!user.get()
                .getParties().stream()
                .map(Party::getId).collect(Collectors.toList())
                .contains(event.get().getParty().getId())) {
            throw new UserNotMemberOfPartyException("User is not member of the event's party.");
        }

        if (isEventFull(event.get())) {
            throw new EventFullException("Event is full.");
        }

        event.get().addParticipant(user.get());

        eventRepository.save(event.get());
    }

    @Override
    public void leaveEvent(Long id, Principal principal) {
        Optional<User> user = userRepository.findById(principal.getId());

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        Optional<Event> event = eventRepository.findById(id);

        if (!event.isPresent()) {
            throw new EventNotExistsException("Event with given id does not exist.");
        }

        event.get().getParticipants().remove(user.get());

        eventRepository.save(event.get());
    }

    @Override
    public EventDTO findById(Long id) {
        return eventRepository.findById(id)
                .map(Mappers::mapEvent)
                .orElseThrow(() -> new EventNotExistsException("Event with given id does not exist."));
    }

    private boolean isEventFull(Event event) {
        return event.getCapacity() != null && event.getParticipants().size() >= event.getCapacity();
    }
}
