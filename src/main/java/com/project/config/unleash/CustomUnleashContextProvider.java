//package com.project.config;
//
//import com.project.config.unleash.RequestContext;
//import io.getunleash.UnleashContext;
//import io.getunleash.UnleashContextProvider;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.annotation.RequestScope;
//
//@Component
//@RequestScope
//public class CustomUnleashContextProvider implements UnleashContextProvider {
//
//    private final RequestContext requestContext;
//
//    public CustomUnleashContextProvider(final RequestContext requestContext) {
//        this.requestContext = requestContext;
//    }
//
//    @Override
//    public UnleashContext getContext() {
//        return UnleashContext.builder()
//                .userId(String.valueOf(requestContext.getUserId()))
//                .build();
//    }
//}
