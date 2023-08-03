/*
 * this class now is not necessary, all the functions comes from the SDK.
 * But you can uncomment the class and adapt it if you want
 * */


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
//@ConfigurationProperties(prefix = "unleash")
//public class UnleashConfiguration {
//
//    @Value("${unleash.appName}")
//    private String appName;
//
//    @Value("${unleash.apiUrl}")
//    private String apiUrl;
//
//    @Value("${unleash.instanceId}")
//    String instanceId;
//
//    @Value("${unleash.clientSecret}")
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
