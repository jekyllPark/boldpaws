package com.boldpaws.apigateway.config;

import com.boldpaws.apigateway.filter.JwtAuthenticationGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RouterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, JwtAuthenticationGatewayFilterFactory filterFactory) {
        log.info("routes invoked, builder : {} / filterFactory : {}", builder, filterFactory);
        return builder.routes()
                /** 멤버 서비스 라우팅 */
                .route(r -> r.path("/member/**", "/oauth2/**", "/static/**", "/views/**")
                        .filters(f ->
                                f
                                        .removeRequestHeader("Cookie")
                                        .rewritePath("/member/(?<remaining>.*)", "/${remaining}")
                        )
                        .uri("lb://MEMBER-SERVICE")
                )
                /** 멤버 서비스 라우팅 - 권한 필요 */
                .route(r -> r.path("/member/actuator/**")
                        .filters(f -> f
                                .removeRequestHeader("Cookie")
                                .rewritePath("/member/(?<remaining>.*)", "/${remaining}")
                                .filter(filterFactory.apply(new JwtAuthenticationGatewayFilterFactory.Config()))
                        )
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}