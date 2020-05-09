package cz.muni.pv112.wannaplaybackend.repository;

import cz.muni.pv112.wannaplaybackend.models.Party;
import cz.muni.pv112.wannaplaybackend.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Part;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Repository
public interface PartyRepository extends PagingAndSortingRepository<Party, Long> {

    List<Party> findByOwner(User owner);
}
