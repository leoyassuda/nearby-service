package com.lny.nearby.repository;

import com.lny.nearby.document.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Get one {@link Place} by placeId.
     *
     * @return a {@link Place}.
     */
    public Place getByPlaceId(String placeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("placeId").is(placeId));
        return mongoTemplate.findOne(query, Place.class);
    }

}
