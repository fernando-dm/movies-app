package com.project.utils.toggles.features;

import java.util.Map;

public interface FeatureToggleService {
    boolean isFeatureToggleActive(String toggleName, Map<String, String> properties);
    boolean isFeatureToggleActive(String toggleName, FeatureContext context);
}
