package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface PartyService {
    /**
     * Creates a party with given owner.
     *
     * @param createPartyDTO data needed for creation
     * @return id of the new party
     */
    long createParty(CreatePartyDTO createPartyDTO);

    /**
     * Finds a party with given id. If no such party exists, null is returned.
     *
     * @param id of the searched party
     * @return found party, or null
     */
    PartyDTO findById(long id);

    /**
     * Finds all parties that are owned by given user.
     *
     * @param userId id of a user
     * @return list of all parties owned by given user
     */
    List<PartyDTO> findUserParties(long userId);
}