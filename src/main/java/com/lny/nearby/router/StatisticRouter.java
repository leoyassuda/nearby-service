package com.lny.nearby.router;

import com.lny.nearby.handler.StatisticHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class StatisticRouter {

    @Bean
    public RouterFunction<ServerResponse> statisticsRoutes(StatisticHandler statisticHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/statistics")
                .and(accept(MediaType.APPLICATION_JSON)), statisticHandler::getStatistics);
    }

}
