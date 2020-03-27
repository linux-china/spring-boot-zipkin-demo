package org.mvnsearch.boot.zipkin.demo;

import brave.propagation.TraceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * portal controller
 *
 * @author linux_china
 */
@RestController
public class PortalController {

    @GetMapping("/")
    public Mono<String> index() {
        return Mono.deferWithContext(context -> {
            if (context.getOrDefault(TraceContext.class, null) != null) {
                //TraceContext traceContext = context.get(TraceContext.class);
                System.out.println("Find trace context");
            }
            return Mono.just("Welcome");
        });
    }
}
