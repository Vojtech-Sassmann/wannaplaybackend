package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.dto.Mappers;
import cz.muni.pv112.wannaplaybackend.models.Event;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.EventRepository;
import cz.muni.pv112.wannaplaybackend.repository.PartyRepository;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.security.EventService;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            throw new UserNotExists("User with given id does not exist.");
        }

        Optional<Party> party = partyRepository.findById(createEventDTO.getPartyId());

        if (!party.isPresent()) {
            throw new PartyNotExists("Party with given id does not exist.");
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
    public List<EventDTO> getAllFutureEvents(Principal principal) {
        Optional<User> user = userRepository.findById(principal.getId());

        if (!user.isPresent()) {
            throw new UserNotExists("User with given id does not exist.");
        }

        return eventRepository.findByDateTimeAfterAndPartyIn(LocalDateTime.now(), user.get().getParties()).stream()
                .map(Mappers::mapEvent)
                .collect(Collectors.toList());
    }
}
