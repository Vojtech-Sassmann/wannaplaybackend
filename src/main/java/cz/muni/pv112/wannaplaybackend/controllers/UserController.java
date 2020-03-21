package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<UserDTO> all() {
        log.info("Called all users.");

        return this.userService.allUsers();
    }

    @PutMapping("")
    public long create(@RequestBody CreateUserDTO createUserDTO) {
        log.info("Called create with: {}", createUserDTO);

        Long id = this.userService.createUser(createUserDTO);

        log.info("Created user with id: {}", id);

        return id;
    }
}
