package com.lny.nearby.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.maps.model.PlacesSearchResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


/**
 * Document representing the details of a place.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Place {

    @Id
    private ObjectId id;

    /**
     * Location representing by an array of double, must be in order <b>[longitude, latitude]</b>.
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private Double[] location;

    /**
     * the place's name.
     */
    @TextIndexed
    private String name;

    /**
     * A URI containing a place's icon.
     */
    private String icon;

    /**
     * The place's identifier. Usually a hash -> [ChIJt3PnnGpZzpQR0uKjcd_MJ64].
     */
    @Indexed(unique = true)
    private String placeId;

    /**
     * The rating of the place.
     */
    private Float rating;

    /**
     * The types for classify places. For examples: [cafe, restaurant, store]
     */
    private String[] types;

    /**
     * The place's address.
     */
    @TextIndexed
    private String address;

    /**
     * The distance in meters from position was informed.
     */
    @Transient
    private Double distance;

    /**
     * A token to control a pagination results.
     */
    @Transient
    private String pageToken;

    /**
     * {@link Integer} to count how many times a place is called in API.
     */
    private Integer count = 1;

    /**
     * The datetime of creation document in database.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createDate;

    /**
     * The datetime of last document update
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * A mapper to convert Google object {@link PlacesSearchResult} to Place Document.
     *
     * @param result the Google place result object to converter.
     * @return a new instance of a Place document.
     */
    public static Place toPlace(PlacesSearchResult result) {
        Place place = new Place();
        place.location = new Double[]{result.geometry.location.lng, result.geometry.location.lat};
        place.name = result.name;
        place.icon = result.icon.toString();
        place.placeId = result.placeId;
        place.rating = result.rating;
        place.types = result.types;
        place.address = result.vicinity;
        return place;
    }
}
