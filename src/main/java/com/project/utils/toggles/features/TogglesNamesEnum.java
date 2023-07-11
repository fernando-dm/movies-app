package com.project.utils.toggles.features;

public enum TogglesNamesEnum implements TogglesNames {
    TENANT_COMPANY_TOGGLE("tenantCompanyToggle");

    private final String toggleName;


    TogglesNamesEnum(String toggleName) {
        this.toggleName = toggleName;
    }

    @Override
    public String getToggleName() {
        return toggleName;
    }
}

