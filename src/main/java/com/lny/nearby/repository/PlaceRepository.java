package com.lny.nearby.repository;

import com.lny.nearby.document.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaceRepository extends ReactiveMongoRepository<Place, String> {

    Mono<Place> findByPlaceId(String placeId);

    Flux<Place> findByLocationNear(Point point);

    Flux<Place> findByLocationNear(Point point, Distance distance);

    Flux<Place> findByLocationNear(Point point, TextCriteria textCriteria);

    Flux<Place> findByLocationNear(Point point, Distance distance, TextCriteria textCriteria);
}
