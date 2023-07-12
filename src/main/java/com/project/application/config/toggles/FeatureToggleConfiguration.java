package com.project.application.config.toggles;

import io.getunleash.Unleash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.workia.application.FeatureToggleService;
import com.workia.application.UnleashFeatureToggleService;

@Configuration
public class FeatureToggleConfiguration {

    @Value("${feature.toggle.service}")
    private String toggleActive;

    private final Unleash unleash;

    public FeatureToggleConfiguration(Unleash unleash) {
        this.unleash = unleash;
    }

    @Bean
    @Primary
    public FeatureToggleService featureToggleService() {
        return switch (toggleActive) {
            case "unleash" -> createUnleashFeatureToggleService();
            case "other" -> createOtherFeatureToggleService();
            default -> throw new IllegalArgumentException("Invalid feature toggle service configuration");
        };
    }

    private FeatureToggleService createUnleashFeatureToggleService() {
        return new UnleashFeatureToggleService(unleash);
    }

    private FeatureToggleService createOtherFeatureToggleService() {
        return null;
//        return new OtherFeatureToggleService(featureManager);
    }

}

