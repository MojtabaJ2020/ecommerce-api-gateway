//package com.ecommerce.api_gateway.filter;
//
//import io.github.resilience4j.bulkhead.Bulkhead;
//import io.github.resilience4j.bulkhead.BulkheadRegistry;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CustomBulkheadGatewayFilter implements GatewayFilter, Ordered {
//
//  private final Bulkhead bulkhead;
//  private final int order;
//
//  // Inject the BulkheadRegistry to retrieve your bulkhead defined via application.yml
//  public CustomBulkheadGatewayFilter(BulkheadRegistry bulkheadRegistry) {
//    // "userServiceBulkhead" should match your configuration name in application.yml
//    this.bulkhead = bulkheadRegistry.bulkhead("userServiceBulkhead");
//    this.order = 0; // set filter order as needed
//  }
//
//  @Override
//  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//    // Use Mono.defer to create a new reactive context per request
//    return Mono.defer(() -> {
//      // Try to acquire a permit from the bulkhead
//      if (bulkhead.tryAcquirePermission()) {
//        // Permit acquired, process the request
//        return chain.filter(exchange)
//                    // When the downstream processing completes (or errors), release the permit
//                    .doFinally(signalType -> bulkhead.releasePermission());
//      } else {
//        // No permit available; reject the request immediately
//        return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Bulkhead limit exceeded"));
//      }
//    });
//  }
//
//  @Override
//  public int getOrder() {
//    return order;
//  }
//}