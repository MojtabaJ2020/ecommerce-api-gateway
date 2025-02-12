package com.ecommerce.api_gateway.filter;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/*
Spring Cloud Gateway expects custom filter classes to follow a specific naming convention.
The class name should end with 'GatewayFilterFactory',
and when referenced in the configuration, this suffix is omitted.
Means, we should refer to this filter as following:

filters:
    - name: Bulkhead
*/

@Component
public class BulkheadGatewayFilterFactory extends AbstractGatewayFilterFactory<BulkheadGatewayFilterFactory.Config> {

  private final BulkheadRegistry bulkheadRegistry;

  public BulkheadGatewayFilterFactory(BulkheadRegistry bulkheadRegistry) {
    super(Config.class);
    this.bulkheadRegistry = bulkheadRegistry;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      Bulkhead bulkhead = bulkheadRegistry.bulkhead(config.getName());

      if (bulkhead.tryAcquirePermission()) {
        return chain.filter(exchange)
                    .doOnSuccess(v -> bulkhead.onComplete())
                    .doOnError(e -> bulkhead.onComplete());
      } else {
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
      }
    };
  }

  public static class Config {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
  }
}
