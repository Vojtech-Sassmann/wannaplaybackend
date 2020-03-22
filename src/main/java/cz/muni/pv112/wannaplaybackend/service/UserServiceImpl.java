package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.Mappers;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long createUser(CreateUserDTO createUserDTO, Principal principal) {
        Optional<User> alreadyCreatedUser = userRepository
                .findByExternalIdentity(principal.getExternalId(), principal.getExternalSource());

        if (alreadyCreatedUser.isPresent()) {
            throw new UserAlreadyExists("There is already created user with given identity");
        }

        User createdUser = userRepository.save(User.builder()
                .nick(createUserDTO.getNick())
                .externalSource(principal.getExternalSource())
                .externalId(principal.getExternalId())
                .build());
        return createdUser.getId();
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository
                .findById(id)
                .map(Mappers::mapUser)
                .orElse(null);
    }

    @Override
    public boolean userExists(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public List<UserDTO> allUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(Mappers::mapUser)
                .collect(Collectors.toList());
    }
}
