package cz.muni.pv112.wannaplaybackend.repository;

import cz.muni.pv112.wannaplaybackend.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.externalId = ?1 and u.externalSource = ?2")
    Optional<User> findByExternalIdentity(String extId, String extSource);
}
