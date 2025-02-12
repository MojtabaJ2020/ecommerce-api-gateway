package com.ecommerce.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class AppConfigs
{
  @Bean
  public KeyResolver userKeyResolver() {
    return new KeyResolver() {
      @Override
      public Mono <String> resolve(ServerWebExchange exchange) {
        // Return the client's host address as the key.
        return Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
//        return Mono.just("1");
      }
    };
  }
  
//  @Bean
//  public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
//                                         CustomBulkheadGatewayFilter customBulkheadGatewayFilter) {
//    return builder.routes()
//                  .route("user-service-route", r -> r.path("/api/v1/users/**")
//                                                     .filters(f -> f.filter(customBulkheadGatewayFilter))
//                                                     .uri("lb://USER-SERVICE"))
//                  .build();
//  }
}
