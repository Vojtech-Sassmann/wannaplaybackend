package cz.muni.pv112.wannaplaybackend;

import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication(scanBasePackageClasses = UserRepository.class, scanBasePackages = "cz.muni.pv112.wannaplaybackend")
public class WannaplayBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WannaplayBackendApplication.class, args);
	}
}
