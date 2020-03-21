package cz.muni.pv112.wannaplaybackend;

import cz.muni.pv112.wannaplaybackend.config.WannaplayProperties;
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

    @Autowired
    public WebMvcConfig(WannaplayProperties wannaplayProperties) {
        this.wannaplayProperties = wannaplayProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(wannaplayProperties));
    }
}
