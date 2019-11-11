package com.lny.nearby.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lny.nearby.document.StatisticAggregate;
import com.lny.nearby.service.StatisticService;
import com.lny.nearby.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class StatisticHandler {

    private static final Logger logger = LoggerFactory.getLogger(StatisticHandler.class);

    @Autowired
    private StatisticService statisticService;

    public Mono<ServerResponse> getStatistics(final ServerRequest serverRequest) {
        logger.info("Handle request to get statistics in the last hour");

        return this.statisticService.getStatistics()
                .flatMap((json) -> ServerResponse.ok()
                        .body(Mono.just(json), StatisticAggregate.class)
                ).onErrorResume(
                        JsonProcessingException.class,
                        (e) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                .body(Mono.just(e.getMessage()), String.class)
                );

    }

}
