package com.lny.nearby.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lny.nearby.document.StatisticAggregate;
import com.lny.nearby.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class StatisticHandler {

    private static final Logger logger = LoggerFactory.getLogger(StatisticHandler.class);

    @Autowired
    private StatisticService statisticService;

    public Mono<ServerResponse> getStatistics(final ServerRequest serverRequest) {

        String requestId = UUID.randomUUID().toString();

        logger.info("StatisticHandler#getStatistics Handle request to get statistics in the last hour requestId={}", requestId);

        return this.statisticService.getStatistics(requestId)
                .flatMap((json) -> ServerResponse.ok()
                        .body(Mono.just(json), StatisticAggregate.class)
                ).onErrorResume(
                        JsonProcessingException.class,
                        (e) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                .body(Mono.just(e.getMessage()), String.class)
                );

    }

}
