package com.project.config.toggles;

import com.project.utils.toggles.features.FeatureToggleService;
import com.project.utils.toggles.features.togglzRepository.TogglzFeatureToggleService;
import com.project.utils.toggles.features.unleashRepository.UnleashFeatureToggleService;
import io.getunleash.Unleash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.togglz.core.manager.FeatureManager;

@Configuration
//@ConfigurationProperties(prefix = "feature")
public class FeatureToggleConfiguration {

    @Value("${feature.toggle.service}")
    private String toggleActive;

    private final FeatureManager featureManager;
    private final Unleash unleash;

    public FeatureToggleConfiguration(FeatureManager featureManager, Unleash unleash) {
        this.featureManager = featureManager;
        this.unleash = unleash;
    }

    @Bean
    @Primary
    public FeatureToggleService featureToggleService() {
        return switch (toggleActive) {
            case "unleash" -> createUnleashFeatureToggleService();
            case "togglz" -> createTogglzFeatureToggleService();
            default -> throw new IllegalArgumentException("Invalid feature toggle service configuration");
        };
    }

    private FeatureToggleService createUnleashFeatureToggleService() {
        return new UnleashFeatureToggleService(unleash);
    }

    private FeatureToggleService createTogglzFeatureToggleService() {
        return new TogglzFeatureToggleService(featureManager);
    }

}

