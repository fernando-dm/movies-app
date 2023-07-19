//package com.project.application.config.unleash;
//
//import io.getunleash.DefaultUnleash;
//import io.getunleash.Unleash;
//import io.getunleash.util.UnleashConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = "feature")
//public class UnleashConfiguration {
//
//    @Value("${feature.appName}")
//    private String appName;
//
//    @Value("${feature.apiUrl}")
//    private String apiUrl;
//
//    @Value("${feature.instanceId}")
//    String instanceId;
//
//    @Value("${feature.clientSecret}")
//    String clientSecret;
//
//    @Bean
//    public Unleash unleash() {
//        UnleashConfig config = new UnleashConfig.Builder()
//                .appName(appName)
//                .instanceId(instanceId)
//                .unleashAPI(apiUrl)
//                .apiKey(clientSecret)
//                .build();
//
//        return new DefaultUnleash(config);
//    }
//}
//
