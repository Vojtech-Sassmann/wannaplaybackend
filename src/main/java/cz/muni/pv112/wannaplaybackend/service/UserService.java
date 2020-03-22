package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface UserService {
    /**
     * Creates user from given data.
     *
     * @param createUserDTO data needed for creation
     * @return id of the new user
     */
    long createUser(CreateUserDTO createUserDTO, Principal principal);

    /**
     * Finds all users that exists.
     *
     * @return list of all users
     */
    List<UserDTO> allUsers();

    /**
     * Finds user by given id. If no such user exists, null is returned.
     *
     * @param id of the searched user
     * @return found user, or null
     */
    UserDTO findById(Long id);

    /**
     * Checks if a user with given id exists.
     *
     * @param id of the searched user
     * @return true, if user with given id exists, false otherwise
     */
    boolean userExists(Long id);
}
