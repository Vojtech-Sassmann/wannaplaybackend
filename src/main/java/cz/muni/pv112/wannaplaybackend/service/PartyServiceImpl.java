package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import cz.muni.pv112.wannaplaybackend.repository.PartyRepository;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
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
    public long createParty(CreatePartyDTO createPartyDTO) {
        Optional<User> owner = userRepository.findById(createPartyDTO.getOwnerId());

        if (!owner.isPresent()) {
            throw new UserNotExists("User with given id does not exist.");
        }

        Party newParty = Party.builder()
                .owner(owner.get())
                .name(createPartyDTO.getName())
                .build();

        return partyRepository.save(newParty).getId();
    }

    @Override
    public List<PartyDTO> findUserParties(long userId) {
        Optional<User> owner = userRepository.findById(userId);

        if (!owner.isPresent()) {
            throw new UserNotExists("User with givne id does not exist.");
        }

        return partyRepository
                .findByOwner(owner.get()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartyDTO findById(long id) {
        return partyRepository
                .findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    private PartyDTO mapToDTO(Party party) {
        return PartyDTO.builder()
                .id(party.getId())
                .name(party.getName())
                .build();
    }
}
