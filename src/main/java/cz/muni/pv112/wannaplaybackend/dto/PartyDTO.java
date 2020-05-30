package cz.muni.pv112.wannaplaybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartyDTO {
    private long id;
    private String name;
    private final Set<PartyMemberDTO> members = new HashSet<>();
    private PartyOwnerDTO owner;

    public void addMember(PartyMemberDTO memberDTO) {
        this.members.add(memberDTO);
    }
}
