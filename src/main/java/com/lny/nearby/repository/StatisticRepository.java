package com.lny.nearby.repository;

import com.lny.nearby.document.StatisticSearched;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StatisticRepository extends ReactiveMongoRepository<StatisticSearched, String> {
}
