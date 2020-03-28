package cz.muni.pv112.wannaplaybackend.dto;

import cz.muni.pv112.wannaplaybackend.models.Event;
import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    public static EventDTO mapEvent(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .capacity(event.getCapacity())
                .dateTime(event.getDateTime())
                .description(event.getDescription())
                .name(event.getName())
                .party(mapEventParty(event.getParty()))
                .owner(mapEventOwner(event.getOwner()))
                .participants(event.getParticipants().stream()
                        .map(Mappers::mapEventParticipant)
                        .collect(Collectors.toList()))
                .build();
    }

    public static EventParticipantDTO mapEventParticipant(User participant) {
        return EventParticipantDTO.builder()
                .id(participant.getId())
                .nick(participant.getNick())
                .build();
    }

    public static EventOwnerDTO mapEventOwner(User owner) {
        return EventOwnerDTO.builder()
                .id(owner.getId())
                .nick(owner.getNick())
                .build();
    }

    public static EventPartyDTO mapEventParty(Party party) {
        return EventPartyDTO.builder()
                .id(party.getId())
                .name(party.getName())
                .build();
    }
}
