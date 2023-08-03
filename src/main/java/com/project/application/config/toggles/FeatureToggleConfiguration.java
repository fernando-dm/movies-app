

/*
* this class now is not necessary, all the functions comes from the SDK.
* But you can uncomment the class and adapt it if you want
* */

//package com.project.application.config.toggles;
//
//import com.company.service.FeatureToggleService;
//import io.getunleash.Unleash;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class FeatureToggleConfiguration {
//
//    @Value("${feature.toggle.service}")
//    private String toggleActive;
//
//    private final Unleash unleash;
//
//    public FeatureToggleConfiguration(Unleash unleash) {
//        this.unleash = unleash;
//    }
//
//    @Bean
//    @Primary
//    public FeatureToggleService featureToggleService() {
//        return switch (toggleActive) {
//            case "unleash" -> createUnleashFeatureToggleService();
//            case "other" -> createOtherFeatureToggleService();
//            default -> throw new IllegalArgumentException("Invalid feature toggle service configuration");
//        };
//    }
//
//    private FeatureToggleService createUnleashFeatureToggleService() {
//        return new UnleashFeatureToggleService(unleash);
//    }
//
//    private FeatureToggleService createOtherFeatureToggleService() {
//        return null;
////        return new OtherFeatureToggleService(featureManager);
//    }
//
//}
//
