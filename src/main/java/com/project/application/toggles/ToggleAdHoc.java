package com.project.application.toggles;

import com.workia.application.TogglesNames;

public enum ToggleAdHoc implements TogglesNames {
    CUSTOM_TENANT_COMPANY_TOGGLE_NAME("tenantCompanyToggle"),
    CUSTOM_TENANT_TOGGLE_NAME("tenantToggle")
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
