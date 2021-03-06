package com.lny.nearby.router;

import com.lny.nearby.handler.PlaceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class PlaceRouter {

    @Bean
    public RouterFunction<ServerResponse> placeRoutes(PlaceHandler placeHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/places")
                .and(accept(MediaType.APPLICATION_JSON)), placeHandler::findPlace);
    }

}
