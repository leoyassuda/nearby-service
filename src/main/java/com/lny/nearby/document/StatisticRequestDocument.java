package com.lny.nearby.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * A simple object to validate fields in functional webflux API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticRequestDocument {
    /**
     * A {@link String} to matching in all attributes. [Optional]
     */
    private String keyword;

    /**
     * The latitude of position of user. [Optional]
     * <b>NOTE: </b> If latitude is present in request, you must include <b>longitude</b> attribute
     */
    private String latitude;

    /**
     * The Longitude of position of user. [Optional]
     * <b>NOTE: </b> If longitude is present in request, you must include <b>latitude</b> attribute
     */
    private String longitude;

    /**
     * A mapper to converter a map of parameter's request to a {@link StatisticRequestDocument}.
     *
     * @param mapParams a map of String containing parameters of request.
     * @return a new {@link StatisticRequestDocument}.
     */
    public static StatisticRequestDocument toStatisticRequestDocument(Map<String, String> mapParams) {
        StatisticRequestDocument placeRequestDocument = new StatisticRequestDocument();

        if (mapParams.isEmpty()) {
            return null;
        } else {
            placeRequestDocument.setKeyword(mapParams.get("keyword"));
            placeRequestDocument.setLatitude(mapParams.get("latitude"));
            placeRequestDocument.setLongitude(mapParams.get("longitude"));
        }

        return placeRequestDocument;
    }
}
