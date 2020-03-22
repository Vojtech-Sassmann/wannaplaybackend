package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.PartyService;
import cz.muni.pv112.wannaplaybackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cz.muni.pv112.wannaplaybackend.security.SecurityInterceptor.PRINCIPAL_ATTR;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final PartyService partyService;

    @Autowired
    public UserController(UserService userService, PartyService partyService) {
        this.userService = userService;
        this.partyService = partyService;
    }

    @GetMapping("")
    public List<UserDTO> all() {
        log.debug("Called all users.");

        return userService.allUsers();
    }

    @PutMapping("")
    public long create(@RequestBody CreateUserDTO createUserDTO, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
        log.debug("Called create with: {}", createUserDTO);

        long id = userService.createUser(createUserDTO, principal);

        log.info("Created user with id: {}", id);

        return id;
    }

    @PostMapping("{id}/exists")
    public boolean doesUserExists(@PathVariable Long id) {
        log.debug("Called doesUserExists: {}", id);

        return userService.userExists(id);
    }

    @GetMapping("{id}/parties")
    public List<PartyDTO> userParties(@PathVariable Long id) {
        log.debug("Called userParties: {}", id);

        return partyService.findUserParties(id);
    }
}
