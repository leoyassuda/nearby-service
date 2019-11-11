package com.lny.nearby.router;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AppRouter {

    @Bean
    RouterFunction<ServerResponse> helloRouterFunction() {
        return RouterFunctions.route(RequestPredicates.path("/"),
                serverRequest ->
                        ServerResponse.ok().body(Mono.just("Ok"), String.class));
    }
}
