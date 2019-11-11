package com.lny.nearby.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A DTO to given a request of statistic API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticAggregate {

    @Id
    private String id;

    @Field
    private String placeId;

    @Field
    private Place place;

    @Field
    private Integer placeCount;

    @Field
    private String textSearched;

    @Field
    private Integer textSearchedCount;

    @Field
    private Double minDistance;

}
