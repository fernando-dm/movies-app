package com.project.utils.toggles.features;

public class FeatureContext {

    private final String tenant;
    private final String companyId;

    public FeatureContext(String tenant, String companyId) {
        this.tenant = tenant;
        this.companyId = companyId;
    }

    public FeatureContext(String tenant) {
        this(tenant, null);
    }

    public String getTenant() {
        return tenant;
    }

    public String getCompanyId() {
        return companyId;
    }
}
