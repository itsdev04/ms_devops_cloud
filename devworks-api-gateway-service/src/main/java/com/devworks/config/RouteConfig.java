package com.devworks.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service-route", r -> r
                        .path("/devworks-product-service/**")
                        .filters(f -> f.rewritePath("/devworks-product-service/?(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://DEVWORKS-PRODUCT-SERVICE"))

                .route("cart-order-service-route", r -> r
                        .path("/devworks-cart-order-service/**")
                        .filters(f -> f.rewritePath("/devworks-cart-order-service/?(?<remaining>.*)", "/${remaining}"))
                        .uri("lb://DEVWORKS-CART-ORDER-SERVICE"))
                .build();

    }
}
