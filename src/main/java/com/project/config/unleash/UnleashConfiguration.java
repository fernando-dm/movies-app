package com.project.config.unleash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import io.getunleash.strategy.Strategy;

@Configuration
@ConfigurationProperties(prefix = "unleash")
public class UnleashConfiguration {

    @Value("${unleash.appName}")
    private String appName;

    @Value("${unleash.apiUrl}")
    private String apiUrl;

    @Value("${unleash.instanceId}")
    String instanceId;

    @Value("${unleash.clientSecret}")
    String clientSecret;

    @Bean
    public Unleash unleash() {
        UnleashConfig config = new UnleashConfig.Builder()
                .appName(appName)
                .instanceId(instanceId)
                .unleashAPI(apiUrl)
                .apiKey(clientSecret)
//                .fallbackStrategy(new HcTenantsStrategy()) puedo activarlo o no
                .build();

        return new DefaultUnleash(config,new HcTenantsStrategy());
    }

    // Define your custom strategy here
//    @Bean
//    public Strategy hcTenantsStrategy() {
//        return new Strategy() {
//            @Override
//            public String getName() {
//                return "tenantId";
//            }
//
//            @Override
//            public boolean isEnabled(Map<String, String> map) {
//                return false;
//            }
//
//
//            @Override
//            public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext, List<Constraint> constraints) {
//                return Strategy.super.isEnabled(parameters, unleashContext, constraints);
//            }
//
//            @Override
//            public boolean isEnabled(Map<String, String> parameters, UnleashContext unleashContext) {
//                String[] tenantIds = parameters.get("tenantId").split(",");
//                String contextTenantId = unleashContext.getProperties().get("tenantId");
//                return Arrays.asList(tenantIds).contains(contextTenantId);
//            }
//        };
//    }
//    @Bean
//    public Strategy hcTenantsStrategy() {
//        return new HcTenantsStrategy();
//    }


//    @Bean
//    public Unleash unleash() {
//        UnleashConfig config = new UnleashConfig.Builder()
//                .appName(appName)
//                .instanceId(appName)
//                .unleashAPI(apiUrl)
////                .customHttpHeader("Authorization", clientSecret)
//                .apiKey(clientSecret)
////                .synchronousFetchOnInitialisation(true)
////                .fallbackStrategy(new HcTenantsStrategy())
//                .build();
//
//        Unleash unleash = new DefaultUnleash(config, new HcTenantsStrategy());
////        return new DefaultUnleash(config);
//        return unleash;
//    }

//    @Bean
//    public UnleashConfig unleashConfig(
//            @Value("${unleash.appName}") String appName,
//            @Value("${unleash.instanceId}") String instanceId,
//            @Value("${unleash.apiUrl}") String apiUrl,
//            @Value("${unleash.clientSecret}") String clientSecret,
//            UnleashContextProvider unleashContextProvider) {
//        return UnleashConfig.builder()
//                .appName(appName)
//                .instanceId(instanceId)
//                .unleashAPI(apiUrl)
//                .unleashContextProvider(unleashContextProvider)
//                .customHttpHeader("Authorization", clientSecret)
//                .build();
//    }
//
//    @Bean
//    public Unleash unleash(UnleashConfig unleashConfig) {
//        return new DefaultUnleash(unleashConfig);
//    }
}

