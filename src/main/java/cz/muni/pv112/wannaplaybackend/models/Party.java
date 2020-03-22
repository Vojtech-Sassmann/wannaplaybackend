package cz.muni.pv112.wannaplaybackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Party {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @NotNull
    @ToString.Exclude
    private User owner;

    @ManyToMany(mappedBy = "parties")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final Set<User> members = new HashSet<>();

    public void addMember(User user) {
        this.members.add(user);
        this.owner.addParty(this);
    }

    public void removeMember(User user) {
        this.members.remove(user);
        this.owner.removeParty(this);
    }
}
