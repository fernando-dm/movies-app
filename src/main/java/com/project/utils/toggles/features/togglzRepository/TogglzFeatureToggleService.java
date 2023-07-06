package com.project.utils.toggles.features.togglzRepository;

import com.project.utils.toggles.features.FeatureToggleService;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Map;

@Service
public class TogglzFeatureToggleService implements FeatureToggleService {
    private final FeatureManager featureManager;
    public TogglzFeatureToggleService(FeatureManager featureManager) {
        this.featureManager = featureManager;
    }

    @Override
    public boolean isFeatureToggleActive(String toggleName, Map<String, String> properties) {
        return false;
    }
}

