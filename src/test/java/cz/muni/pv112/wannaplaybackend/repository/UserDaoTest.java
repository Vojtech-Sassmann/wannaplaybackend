package cz.muni.pv112.wannaplaybackend.repository;

import cz.muni.pv112.wannaplaybackend.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserDaoTest {

    @Autowired private DataSource dataSource;
    @Autowired private UserRepository userRepository;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    void save() {
        User user = User.builder()
                .nick("vectoun")
                .externalId("sdfsdf")
                .externalSource("facebook")
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser.isPresent());
    }
}
