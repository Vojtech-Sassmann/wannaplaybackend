package cz.muni.pv112.wannaplaybackend.service.impl;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.Mappers;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.PartyRepository;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.PartyService;
import cz.muni.pv112.wannaplaybackend.service.exceptions.PartyNotExistsException;
import cz.muni.pv112.wannaplaybackend.service.exceptions.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new UserNotExistsException("User with given id does not exist.");
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
    public List<PartyDTO> findUserOwnedParties(long userId) {
        Optional<User> owner = userRepository.findById(userId);

        if (!owner.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        return partyRepository
                .findByOwner(owner.get()).stream()
                .map(Mappers::mapParty)
                .collect(Collectors.toList());
    }

    @Override
    public List<PartyDTO> findUserMemberParties(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        return user.get().getParties().stream()
                .map(Mappers::mapParty)
                .collect(Collectors.toList());
    }

    @Override
    public void joinParty(Long partyId, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        Optional<Party> party = partyRepository.findById(partyId);

        if (!party.isPresent()) {
            throw new PartyNotExistsException("Party with given id does not exist.");
        }

        party.get().addMember(user.get());

        partyRepository.save(party.get());
    }

    @Override
    public void leaveParty(Long partyId, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new UserNotExistsException("User with given id does not exist.");
        }

        Optional<Party> party = partyRepository.findById(partyId);

        if (!party.isPresent()) {
            throw new PartyNotExistsException("Party with given id does not exist.");
        }

        party.get().getMembers().remove(user.get());

        partyRepository.save(party.get());
    }

    @Override
    public PartyDTO findById(long id) {
        return partyRepository
                .findById(id)
                .map(Mappers::mapParty)
                .orElseThrow(() -> new PartyNotExistsException("Party with given id does not exist."));
    }
}
