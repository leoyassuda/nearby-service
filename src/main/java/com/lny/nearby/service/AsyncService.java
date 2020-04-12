package com.lny.nearby.service;

import com.lny.nearby.document.Place;
import com.lny.nearby.document.StatisticSearched;
import com.lny.nearby.repository.PlaceRepository;
import com.lny.nearby.repository.StatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    /**
     * Used to save places in database for getting statistics and/or when the external API is unavailable.<br>
     * NOTE: This method runs in a different thread.
     *
     * @param placeList a {@link List}  of {@link Place} to process.
     */
    @Async("threadPoolTaskExecutor")
    public void processPlaces(List<Place> placeList) {
        logger.info("AsyncService#processPlaces Process places asynchronously thread={} placeList={}",
                Thread.currentThread().getName(), placeList);

        for (Place place : placeList) {
            Mono<Place> placeDocument = placeRepository.findByPlaceId(place.getPlaceId());
            placeDocument
                    .flatMap(placeUpdate -> {
                        placeUpdate.setCount(placeUpdate.getCount() + 1);
                        placeUpdate.setLastModifiedDate(LocalDateTime.now());
                        try {
                            placeRepository.save(placeUpdate).subscribe();
                        } catch (DuplicateKeyException e) {
                            logger.error("AsyncService#process Places Duplicate key error collection: " +
                                    "nearby.place index: placeId " + place.getPlaceId());
                        }
                        return Mono.just(placeUpdate);
                    })
                    .thenEmpty(subscriber -> {
                        place.setCreateDate(LocalDateTime.now());
                        placeRepository.save(place).subscribe();
                    })
                    .subscribe();
        }
    }

    /**
     * Process a list of places to a statistic data.<br>
     * NOTE: This method runs in a different thread.
     *
     * @param placeList a {@link List} of {@link Place} found in API's result.
     * @param keyword   a {@link String} of text searched.
     */
    @Async("threadPoolTaskExecutor")
    public void processStatistic(List<Place> placeList, String keyword) {
        logger.info("AsyncService#processStatistic Process statistic asynchronously thread={} placeList={} keyword={}",
                Thread.currentThread().getName(), placeList, keyword);

        for (Place place : placeList) {
            StatisticSearched statisticSearched = new StatisticSearched();
            statisticSearched.setPlaceId(place.getPlaceId());
            statisticSearched.setTextSearched(keyword);
            statisticSearched.setDistance(place.getDistance());
            statisticSearched.setCreateDate(LocalDateTime.now());
            statisticRepository.save(statisticSearched).subscribe();
        }
    }

}
