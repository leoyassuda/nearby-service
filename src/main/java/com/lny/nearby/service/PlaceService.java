package com.lny.nearby.service;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import com.lny.nearby.document.Place;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;

/**
 * Service to provide and process place's data.
 */
@Service
public class PlaceService {

    @Value("${app.gk}")
    private String gk;

    @Async
    public Flux<PlacesSearchResult> findPlace(LatLng latLng, String keyword) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(gk)
                .build();

        NearbySearchRequest places = PlacesApi.nearbySearchQuery(context, latLng);
        places.keyword(keyword);
//        places.radius(1000);
        places.rankby(RankBy.DISTANCE);

        try {

            PlacesSearchResponse response = places.await();
            PlacesSearchResult[] results = response.results;

            for (PlacesSearchResult result : results) {
                Place place = Place.toPlace(result);
                System.out.println(place);
            }

            return Flux.fromArray(results);
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return Flux.empty();
    }
}
