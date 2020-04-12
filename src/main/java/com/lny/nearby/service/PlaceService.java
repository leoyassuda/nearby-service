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
import com.lny.nearby.repository.PlaceRepository;
import com.lny.nearby.util.CalculatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to provide and process place's data.
 */
@Service
public class PlaceService {

    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    @Value("${app.gk}")
    private String gk;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private PlaceRepository placeRepository;

    /**
     * Find places nearby the position informed
     *
     * @param requestId the id for each request
     * @param latitude  the latitude of position
     * @param longitude the longitude of position
     * @param keyword   a text to matching any places's attributes
     * @param rankBy    the order of places
     * @return a {@link Flux} of Places.
     */
    public Flux<Place> findPlace(String requestId, Double latitude, Double longitude, String keyword, String rankBy) {

        logger.info("PlaceService#PlaceService requestId={} latitude={} longitude={} keyword={} rankBy={}",
                requestId, latitude, longitude, keyword, rankBy);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(gk)
                .build();

        final LatLng latLng = new LatLng(latitude, longitude);

        NearbySearchRequest places = PlacesApi.nearbySearchQuery(context, latLng);

        if (!StringUtils.isEmpty(keyword)) {
            places.keyword(keyword);
        }

        if (!StringUtils.isEmpty(rankBy)) {
            places.rankby(RankBy.valueOf(rankBy.toLowerCase()));
        } else {
            places.radius(1000);
        }

        try {
            PlacesSearchResponse response = places.await();
            PlacesSearchResult[] results = response.results;

            List<Place> placeList = new ArrayList<>();
            for (PlacesSearchResult result : results) {
                Place place = Place.toPlace(result);
                double distanceFromPosition = CalculatorUtils.distance(latitude, longitude,
                        place.getLocation()[1], place.getLocation()[0], "K") * 1000;
                place.setDistance(CalculatorUtils.roundDouble(distanceFromPosition, 2));
                placeList.add(place);
            }

            asyncService.processPlaces(placeList);
            asyncService.processStatistic(placeList, keyword);

            return Flux.fromIterable(placeList);
        } catch (ApiException apiException) {
            logger.error("PlaceService#findPlace Error to call Google Maps API", apiException);
            return this.findPlaceMongo(requestId, latitude, longitude, keyword);
        } catch (InterruptedException | IOException e) {
            logger.error("PlaceService#findPlace Error to receive Google Maps API response", e);
            return this.findPlaceMongo(requestId, latitude, longitude, keyword);
        }
    }

    /**
     * This method is used when the external API is unavailable.
     *
     * @param latitude  the latitude of position.
     * @param longitude the longitude of position.
     * @param keyword   the text to matching in attributes.
     * @return a {@link List} of {@link Place}.
     */
    private Flux<Place> findPlaceMongo(String requestId, Double latitude, Double longitude, String keyword) {
        logger.info("PlaceService#findPlace finding places in database requestId={} latitude={} longitude={} keyword={}",
                requestId, latitude, longitude, keyword);
        if (keyword.isEmpty()) {
            return placeRepository.findByLocationNear(
                    new Point(longitude, latitude),
                    new Distance(1, Metrics.KILOMETERS));
        } else {
            TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);
            return placeRepository.findByLocationNear(
                    new Point(longitude, latitude),
                    new Distance(1, Metrics.KILOMETERS),
                    criteria);
        }
    }
}
