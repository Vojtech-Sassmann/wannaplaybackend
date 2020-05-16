package cz.muni.pv112.wannaplaybackend.controllers;

import com.google.common.collect.Lists;
import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.EventDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.EventService;
import cz.muni.pv112.wannaplaybackend.service.PartyService;
import cz.muni.pv112.wannaplaybackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cz.muni.pv112.wannaplaybackend.security.SecurityInterceptor.PRINCIPAL_ATTR;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final PartyService partyService;
    private final EventService eventService;

    @Autowired
    public UserController(UserService userService, PartyService partyService, EventService eventService) {
        this.userService = userService;
        this.partyService = partyService;
        this.eventService = eventService;
    }

    @GetMapping("logged-user")
    public UserDTO getPrincipal(@RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Called getPrincipal");

        return userService.findById(principal.getId());
    }

    @GetMapping("user")
    public List<UserDTO> all() {
        log.debug("Called all users.");

        return userService.allUsers();
    }

    @PutMapping("user")
    public long create(@RequestBody CreateUserDTO createUserDTO, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Called create with: {}", createUserDTO);

        long id = userService.createUser(createUserDTO, principal);

        log.info("Created user with id: {}", id);

        return id;
    }

    @GetMapping("user-exists")
    public boolean doesUserExists(@RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Called doesUserExists.");

        return principal.getId() != null;
    }

    @GetMapping("user/{id}/owned-parties")
    public List<PartyDTO> ownedParties(@PathVariable Long id) {
        log.debug("Called userParties: {}", id);

        return partyService.findUserOwnedParties(id);
    }

    @GetMapping("user/{id}/member-parties")
    public List<PartyDTO> memberParties(@PathVariable Long id) {
        log.debug("Called memberParties");

        return partyService.findUserMemberParties(id);
    }

    @GetMapping("user/{id}/parties")
    public List<PartyDTO> parties(@PathVariable Long id) {
        log.debug("Called parties");

        Set<PartyDTO> parties = new HashSet<>();
        parties.addAll(partyService.findUserOwnedParties(id));
        parties.addAll(partyService.findUserMemberParties(id));
        return new ArrayList<>(parties);
    }

    @GetMapping("user/{id}/future-events")
    public List<EventDTO> getAllFutureEvents(@PathVariable("id") Long userId) {
        log.debug("GetAllFutureEvents called {}.", userId);

        return eventService.getAllFutureEvents(userId);
    }

    @GetMapping("user/{id}/events")
    public List<EventDTO> getUserFutureEvents(@PathVariable("id") Long userId) {
        log.debug("GetUserFutureEvents called with {}.", userId);

        return eventService.getUserFutureEvents(userId);
    }
}
