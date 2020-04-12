package com.lny.nearby.service;

import com.lny.nearby.document.StatisticAggregate;
import com.lny.nearby.repository.PlaceRepositoryCustom;
import com.lny.nearby.repository.StatisticRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service to provide and process place's data.
 */
@Service
public class StatisticService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticService.class);

    @Autowired
    private PlaceRepositoryCustom placeRepositoryCustom;

    @Autowired
    private StatisticRepositoryCustom statisticRepositoryCustom;

    /**
     * Get statistic about the places API in the last hour.
     *
     * @return a {@link Mono} with {@link StatisticAggregate} populated.
     */
    public Mono<StatisticAggregate> getStatistics(String requestId) {
        logger.info("StatisticService#getStatistics Getting statistics in the last hour requestId={}", requestId);

        StatisticAggregate resultAggregate = statisticRepositoryCustom.getMinDistance();
        StatisticAggregate topPlace = statisticRepositoryCustom.getTopCountPlace();
        StatisticAggregate topText = statisticRepositoryCustom.getTopCountTextSearched();

        resultAggregate.setPlaceId(topPlace.getId());
        resultAggregate.setPlaceCount(topPlace.getPlaceCount());

        resultAggregate.setTextSearched(topText.getId());
        resultAggregate.setTextSearchedCount(topText.getTextSearchedCount());

        resultAggregate.setPlace(placeRepositoryCustom.getByPlaceId(resultAggregate.getPlaceId()));
        resultAggregate.getPlace().setId(null);

        return Mono.just(resultAggregate);
    }

}
