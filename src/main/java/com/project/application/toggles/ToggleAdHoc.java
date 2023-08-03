package com.project.application.toggles;


import com.company.toggles.TogglesNames;

public enum ToggleAdHoc implements TogglesNames {

    // note that toggle name is the same as unleash server
    CUSTOM_TENANT_COMPANY_TOGGLE_NAME("premiumByTenant"),
    CUSTOM_TENANT_TOGGLE_NAME("otherTenantToggle")
    ;

    private final String toggleName;

    ToggleAdHoc(String toggleName) {
        this.toggleName = toggleName;
    }

    @Override
    public String getToggleName() {
        return toggleName;
    }
}
