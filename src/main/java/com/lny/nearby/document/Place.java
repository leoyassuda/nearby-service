package com.lny.nearby.document;

import com.google.maps.model.PlacesSearchResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;


/**
 * Document representing the details of a place.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    private ObjectId id;

    /**
     * Location representing by an array of double, in order [longitude, latitude].
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2D)
    private double[] location;

    /**
     * the place's name.
     */
    private String name;

    /**
     * A URI containing a place's icon.
     */
    private String icon;

    /**
     * The place's identifier. Usually a hash -> [ChIJt3PnnGpZzpQR0uKjcd_MJ64].
     */
    private String placeId;

    /**
     * The rating of the place.
     */
    private double rating;

    /**
     * The types for classify places. For examples: [cafe, restaurant, store]
     */
    private String[] types;

    /**
     * The place's address.
     */
    private String address;

    /**
     * A mapper to convert Google object {@link PlacesSearchResult} to Place Document.
     *
     * @param result the Google place result object to converter.
     * @return a new instance of a Place document.
     */
    public static Place toPlace(PlacesSearchResult result) {
        Place place = new Place();
        place.location = new double[]{result.geometry.location.lng, result.geometry.location.lat};
        place.name = result.name;
        place.icon = result.icon.toString();
        place.placeId = result.placeId;
        place.rating = result.rating;
        place.types = result.types;
        place.address = result.vicinity;
        return place;
    }
}
