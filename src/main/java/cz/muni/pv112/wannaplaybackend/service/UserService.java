package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface UserService {
    Long createUser(CreateUserDTO createUserDTO);
    List<UserDTO> allUsers();
}
