package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.security.EventService;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cz.muni.pv112.wannaplaybackend.security.SecurityInterceptor.PRINCIPAL_ATTR;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PutMapping("event")
    public long create(@RequestBody CreateEventDTO createEventDTO, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Create event called with: {}", createEventDTO);

        Long id = eventService.createEvent(createEventDTO, principal);

        log.info("Created event with id: {}", id);

        return id;
    }

    @GetMapping("future-events")
    public List<EventDTO> getAllFutureEvents(@RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("GetAllFutureEvents called.");

        return eventService.getAllFutureEvents(principal);
    }
}
