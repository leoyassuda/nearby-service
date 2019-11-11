package com.lny.nearby.repository;

import com.lny.nearby.document.StatisticAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class StatisticRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(StatisticRepositoryCustom.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final LocalDateTime localDateTime = LocalDateTime.now().minus(1, ChronoUnit.HOURS);

    private final MatchOperation matchOperation = Aggregation.match(
            Criteria.where("createDate").gte(localDateTime)
    );

    private final LimitOperation limitToOnlyFirstDoc = limit(1);

    /**
     * Aggregation to get the minimum distance.
     *
     * @return a {@link StatisticAggregate} with the minimum distance.
     */
    public StatisticAggregate getMinDistance() {

        logger.info("Aggregating data of minimum distance statistic");

        GroupOperation groupByAndMinDistance = group("placeId")
                .min("distance").as("minDistance");

        Aggregation aggregation = newAggregation(
                matchOperation,
                groupByAndMinDistance,
                limitToOnlyFirstDoc);

        AggregationResults<StatisticAggregate> result = mongoTemplate.aggregate(
                aggregation, "statisticSearched", StatisticAggregate.class);

        return result.getUniqueMappedResult();
    }

    /**
     * Aggregation to get the top place requested in API.
     *
     * @return a {@link StatisticAggregate} with top place searched attributes.
     */
    public StatisticAggregate getTopCountPlace() {

        logger.info("Aggregating data of top place searched statistic");


        GroupOperation groupByTopPlaceSearched = group("placeId")
                .count().as("placeCount");

        Aggregation aggregation = newAggregation(
                matchOperation,
                groupByTopPlaceSearched,
                limitToOnlyFirstDoc);

        AggregationResults<StatisticAggregate> result = mongoTemplate.aggregate(
                aggregation, "statisticSearched", StatisticAggregate.class);

        return result.getUniqueMappedResult();
    }

    /**
     * Aggregation to get the top text searched requested in API.
     *
     * @return a {@link StatisticAggregate} with top text searched attributes.
     */
    public StatisticAggregate getTopCountTextSearched() {

        logger.info("Aggregating data of the most text searched statistic");

        GroupOperation groupByTopPlaceSearched = group("textSearched")
                .count().as("textSearchedCount");

        Aggregation aggregation = newAggregation(
                matchOperation,
                groupByTopPlaceSearched,
                limitToOnlyFirstDoc);

        AggregationResults<StatisticAggregate> result = mongoTemplate.aggregate(
                aggregation, "statisticSearched", StatisticAggregate.class);

        return result.getUniqueMappedResult();
    }
}
