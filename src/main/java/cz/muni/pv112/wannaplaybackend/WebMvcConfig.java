package cz.muni.pv112.wannaplaybackend;

import cz.muni.pv112.wannaplaybackend.config.WannaplayProperties;
import cz.muni.pv112.wannaplaybackend.repository.UserRepository;
import cz.muni.pv112.wannaplaybackend.security.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final WannaplayProperties wannaplayProperties;
    private final UserRepository userRepository;

    @Autowired
    public WebMvcConfig(WannaplayProperties wannaplayProperties, UserRepository userRepository) {
        this.wannaplayProperties = wannaplayProperties;
        this.userRepository = userRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(wannaplayProperties, userRepository));
    }
}
