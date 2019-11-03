package com.lny.nearby.handler;

import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;
import com.lny.nearby.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * A handler to provide a reactive process across {@link com.lny.nearby.router.PlaceRouter} to the {@link PlaceService}.
 */
@Component
public class PlaceHandler {

    @Autowired
    private PlaceService placeService;

    public Mono<ServerResponse> findPlace(ServerRequest serverRequest) {
        //TODO: receber param do request e passar para o service.
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(placeService.findPlace(new LatLng(new Double("-23.5668698"), new Double("-46.6608874")), "starbucks"), PlacesSearchResult.class);
    }
}
