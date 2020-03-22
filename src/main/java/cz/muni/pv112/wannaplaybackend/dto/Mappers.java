package cz.muni.pv112.wannaplaybackend.dto;

import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class Mappers {

    public static PartyMemberDTO mapPartyMember(User user) {
        return PartyMemberDTO.builder()
                .id(user.getId())
                .nick(user.getNick())
                .build();
    }

    public static PartyDTO mapParty(Party party) {
        PartyDTO partyDTO = PartyDTO.builder()
                .id(party.getId())
                .name(party.getName())
                .build();

        party.getMembers().forEach(m -> partyDTO.addMember(mapPartyMember(m)));

        return partyDTO;
    }

    public static UserDTO mapUser(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .externalId(user.getExternalId())
                .externalSource(user.getExternalSource())
                .nick(user.getNick())
                .build();
    }
}
