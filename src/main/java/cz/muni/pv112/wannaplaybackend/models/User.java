package cz.muni.pv112.wannaplaybackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String nick;

    @NotNull
    @NotEmpty
    private String externalId;

    @NotNull
    @NotEmpty
    private String externalSource;

    @OneToMany
    private Set<Party> OwnedParties = new HashSet<>();
}
