package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.dto.CreateUserDTO;
import cz.muni.pv112.wannaplaybackend.dto.UserDTO;
import cz.muni.pv112.wannaplaybackend.models.User;
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
    public Long createUser(CreateUserDTO createUserDTO) {
        Optional<User> alreadyCreatedUser = userRepository
                .findByExternalIdentity(createUserDTO.getExternalId(), createUserDTO.getExternalSource());

        if (alreadyCreatedUser.isPresent()) {
            throw new UserAlreadyExists("There is already created user with given identity");
        }

        User createdUser = userRepository.save(User.builder()
                .nick(createUserDTO.getNick())
                .externalSource(createUserDTO.getExternalSource())
                .externalId(createUserDTO.getExternalId())
                .build());
        return createdUser.getId();
    }

    @Override
    public List<UserDTO> allUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .externalId(user.getExternalId())
                .externalSource(user.getExternalSource())
                .nick(user.getNick())
                .build();
    }
}
