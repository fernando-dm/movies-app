package com.project.utils.toggles.features.unleashRepository;

import com.project.utils.toggles.features.FeatureToggleService;
import io.getunleash.Unleash;
import io.getunleash.UnleashContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UnleashFeatureToggleService implements FeatureToggleService {
    private final Unleash unleash;

    public UnleashFeatureToggleService(Unleash unleash) {
        this.unleash = unleash;
    }

    @Override
    public boolean isFeatureToggleActive(String toggleName, Map<String, String> properties) {
        UnleashContext.Builder contextBuilder = UnleashContext
                .builder()
                .appName("movies-app"); // TODO no es necesario? averiguar

        properties.forEach(contextBuilder::addProperty);
        UnleashContext context = contextBuilder.build();
        return unleash.isEnabled(toggleName, context);
    }
}

