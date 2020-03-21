package cz.muni.pv112.wannaplaybackend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Getter
@Setter
@PropertySource("file:/etc/wannaplay/wannaplay.properties")
@Configuration
@Component
public class WannaplayProperties {

    @Value( "${facebook.client.id}" )
    private String facebookClientId;

    @Value("${facebook.client.secret}")
    private String facebookClientSecret;
}
