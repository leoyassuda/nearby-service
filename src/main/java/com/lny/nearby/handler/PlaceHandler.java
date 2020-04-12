package com.lny.nearby.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lny.nearby.document.PlaceRequestDocument;
import com.lny.nearby.service.PlaceService;
import com.lny.nearby.util.JsonUtils;
import com.lny.nearby.validator.PlaceRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * A handler to provide a reactive process across {@link com.lny.nearby.router.PlaceRouter} to the {@link PlaceService}.
 */
@Component
public class PlaceHandler {

    private static final Logger logger = LoggerFactory.getLogger(PlaceHandler.class);

    @Autowired
    private PlaceService placeService;

    private final Validator validator = new PlaceRequestValidator();

    /**
     * Handler to process a request findPlace API.
     *
     * @return a list of places.
     */
    public Mono<ServerResponse> findPlace(final ServerRequest serverRequest) {
        String requestId = UUID.randomUUID().toString();

        PlaceRequestDocument placeRequestDocument = PlaceRequestDocument.toPlaceRequestDocument(serverRequest.queryParams().toSingleValueMap());

        Errors errors = new BeanPropertyBindingResult(
                placeRequestDocument,
                PlaceRequestDocument.class.getName());

        validator.validate(placeRequestDocument, errors);

        if (errors.getAllErrors().isEmpty()) {
            return placeService.findPlace(requestId,
                    Double.parseDouble(placeRequestDocument.getLatitude()),
                    Double.parseDouble(placeRequestDocument.getLongitude()), placeRequestDocument.getKeyword(),
                    placeRequestDocument.getRankedBy())
                    .collectList()
                    .flatMap(JsonUtils::write)
                    .flatMap((json) -> ServerResponse.ok()
                            .body(Mono.just(json), String.class)
                    ).onErrorResume(
                            JsonProcessingException.class,
                            (e) -> ServerResponse.status(INTERNAL_SERVER_ERROR)
                                    .body(Mono.just(e.getMessage()), String.class)
                    );
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }

}
