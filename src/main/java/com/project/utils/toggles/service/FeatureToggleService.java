package com.project.utils.toggles.service;

import io.getunleash.UnleashContext;
import io.getunleash.Unleash;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FeatureToggleService {
    private final Unleash unleash;

    public FeatureToggleService(Unleash unleash) {
        this.unleash = unleash;
    }


    public boolean isFeatureToggleActive2(String toggleName, Map<String, String> properties) {
        UnleashContext.Builder contextBuilder = UnleashContext
                .builder()
                .appName("movies-app");  //no lo veo necesario

        properties.forEach(contextBuilder::addProperty);

        UnleashContext context = contextBuilder.build();

        return unleash.isEnabled(toggleName, context);
    }

    //    public boolean isFeatureToggleActive(String toggleName, String tenantId, String company) {
//        UnleashContext context = UnleashContext.builder()
//                .appName("movies-app")
//                .addProperty("tenant", tenantId)
//                .addProperty("company", company)
//                .build();
//
//        return unleash.isEnabled(toggleName, context);
//    }
}

