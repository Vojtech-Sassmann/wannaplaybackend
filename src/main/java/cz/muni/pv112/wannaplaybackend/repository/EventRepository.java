package cz.muni.pv112.wannaplaybackend.repository;

import cz.muni.pv112.wannaplaybackend.models.Event;
import cz.muni.pv112.wannaplaybackend.models.Party;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    List<Event> findByDateTimeAfterAndPartyIn(LocalDateTime dateTime, Set<Party> parties);
}
