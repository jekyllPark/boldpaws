package com.boldpaws.apigateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RouterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/member/**")
                        .filters(f -> f.rewritePath("/member/(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://MEMBER-SERVICE"))
                .build();
    }
}