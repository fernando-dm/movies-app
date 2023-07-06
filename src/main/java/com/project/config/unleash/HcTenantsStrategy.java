package com.project.config.unleash;

import io.getunleash.Constraint;
import io.getunleash.UnleashContext;
import io.getunleash.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HcTenantsStrategy implements Strategy {
    private final static Logger logger = LoggerFactory.getLogger(HcTenantsStrategy.class);

    @Override
    public String getName() {
        return "tenant";
    }

    @Override
    public boolean isEnabled(Map<String, String> map) {
        return false;
    }

    @Override  // aca deberia entrar, pero sin embargo no se sobre escribe, va directo al isEnabled de unleash
    public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext) {
        String[] tenantIds = parameters.get("tenant").split(",");
        String contextTenantId = unleashContext.getProperties().get("tenant");
        return Arrays.asList(tenantIds).contains(contextTenantId);
    }


    @Override
    public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext, List<Constraint> constraints) {
        return Strategy.super.isEnabled(parameters, unleashContext, constraints);
    }

//    @Override
//    public boolean isEnabled(Map<String, String> map) {
//        return false;
//    }

//    @Override
//    public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext) {
//        String[] tenantIds = parameters.get("tenantId").split(",");
//        Optional<String> contextTenantId = unleashContext.getUserId();
//        return Arrays.asList(tenantIds).contains(contextTenantId);
////        return Strategy.super.isEnabled(parameters, unleashContext);
//    }

//    @Override
//    public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext, List<Constraint> constraints) {
//        return Strategy.super.isEnabled(parameters, unleashContext, constraints);
//    }

//    @Override
//    public boolean isEnabled(Map<String, String> parameters, String name) {
//        String[] tenantIds = parameters.get("tenantId").split(",");
//        String contextTenantId = UnleashContextProvider.getContext().getUserId();
//        return Arrays.asList(tenantIds).contains(contextTenantId);
//    }
}

//public class Main {
//    public static void main(String[] args) {
//        UnleashConfig config = UnleashConfig.builder()
//                .appName("my-application")
//                .unleashAPI("http://localhost:4242/api/")
//                .refreshInterval(1000)
//                .unleashAPIKey("default:development.unleash-insecure-api-token")
//                .build();
//
//        UnleashContext context = UnleashContext.builder()
//                .userId("62d841d446e0fb0001e495e3")
//                .build();
//
//        Unleash unleash = new DefaultUnleash(config, new HcTenantsStrategy());
//
//        System.out.println("Fetching toggles from: http://unleash.herokuapp.com");
//
//        while (true) {
//            boolean enabled = unleash.isEnabled("HcAuth0", context);
//            System.out.println("HcAuth0: " + (enabled ? "Feature Flags habilitado para este cliente" : "off"));
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
