package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreateEventDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("event/{id}")
    public EventDTO findById(@PathVariable Long id) {
        log.debug("Find event by id called.");

        return eventService.findById(id);
    }

    @PutMapping("event")
    public long create(@RequestBody CreateEventDTO createEventDTO, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Create event called with: {}", createEventDTO);

        Long id = eventService.createEvent(createEventDTO, principal);

        log.info("Created event with id: {}", id);

        return id;
    }

    @PostMapping("event/{id}/join")
    public void joinEvent(@PathVariable("id") Long id, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Join event called.");

        eventService.joinEvent(id, principal);
    }

    @PostMapping("event/{id}/leave")
    public void leaveEvent(@PathVariable("id") Long id, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Leave event called.");

        eventService.leaveEvent(id, principal);
    }
}
