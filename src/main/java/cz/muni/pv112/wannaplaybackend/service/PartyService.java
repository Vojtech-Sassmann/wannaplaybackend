package cz.muni.pv112.wannaplaybackend.service;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;

import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public interface PartyService {
    /**
     * Creates a party with given owner. The owner also becomes a
     * member of the party.
     *
     * @param createPartyDTO data needed for creation
     * @return id of the new party
     */
    long createParty(CreatePartyDTO createPartyDTO, Principal principal);

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
    List<PartyDTO> findUserOwnedParties(long userId);

    /**
     * Finds all parties that the given user is a member of.
     *
     * @param userId id of a user
     * @return list of all parties owned by given user
     */
    List<PartyDTO> findUserMemberParties(long userId);

    /**
     * Add user with given id to the party with given id.
     *
     *
     * @param partyId party id
     * @param userId user id
     */
    void joinParty(Long partyId, Long userId);

    /**
     * Remove user with given id from the party with given id.
     *
     * @param partyId party id
     * @param userId user id
     */
    void leaveParty(Long partyId, Long userId);
}
