package com.devworks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class FallbackController {
    @GetMapping("/product-service-fallback")
    public Mono<String> productCircuitBreakerFallback(){
        return Mono.just("Product Service is taking longer than expected. Please try again later.");
    }
}
