package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.Mappers;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.PartyRepository;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;

    @Autowired
    public PartyServiceImpl(PartyRepository partyRepository, UserRepository userRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long createParty(CreatePartyDTO createPartyDTO, Principal principal) {
        Optional<User> owner = userRepository.findById(principal.getId());

        if (!owner.isPresent()) {
            throw new UserNotExists("User with given id does not exist.");
        }

        Party newParty = Party.builder()
                .owner(owner.get())
                .name(createPartyDTO.getName())
                .build();
        newParty.addMember(owner.get());

        newParty = partyRepository.save(newParty);

        return newParty.getId();
    }

    @Override
    public List<PartyDTO> findUserParties(long userId) {
        Optional<User> owner = userRepository.findById(userId);

        if (!owner.isPresent()) {
            throw new UserNotExists("User with givne id does not exist.");
        }

        return partyRepository
                .findByOwner(owner.get()).stream()
                .map(Mappers::mapParty)
                .collect(Collectors.toList());
    }

    @Override
    public PartyDTO findById(long id) {
        return partyRepository
                .findById(id)
                .map(Mappers::mapParty)
                .orElse(null);
    }
}
