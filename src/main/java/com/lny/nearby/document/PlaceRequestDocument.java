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
public class PlaceRequestDocument {
    /**
     * A {@link String} to matching in all attributes. [Optional]
     */
    private String keyword;

    /**
     * The latitude of position to search a place. [Required]
     */
    private String latitude;

    /**
     * The Longitude of position to search a place. [Required]
     */
    private String longitude;

    /**
     * The distance in <b>meters</b> according the position as informed. [Required] <br>
     * <b>NOTE: </b> If radius is present in request, you must not include <b>rankedBy</b> attribute
     */
    private String radius;

    /**
     * The place's name.
     */
    private String name;

    /**
     * The order in results are listed. [Required]
     * <ul>
     *     <li><b>prominence: </b> (Default) relevance of a result</li>
     *     <li><b>distance: </b> ascending based in order distance from the specified location.</li>
     *     If <b>distance</b> is specified, one or more of <b>keyword</b>, <b>name</b> or <b>type</b> is required</li>
     * </ul>
     */
    private String rankedBy;

    /**
     * The type of a place to restrict results. [Optional]<br>
     * See @{@link Type}.
     */
    private String type;

    /**
     * A mapper to converter a map of parameter's request to a {@link PlaceRequestDocument}.
     *
     * @param mapParams a map of String containing parameters of request.
     * @return a new {@link PlaceRequestDocument}.
     */
    public static PlaceRequestDocument toPlaceRequestDocument(Map<String, String> mapParams) {
        PlaceRequestDocument placeRequestDocument = new PlaceRequestDocument();

        if (mapParams.isEmpty()) {
            return null;
        } else {
            placeRequestDocument.setKeyword(mapParams.get("keyword"));
            placeRequestDocument.setLatitude(mapParams.get("latitude"));
            placeRequestDocument.setLongitude(mapParams.get("longitude"));
            placeRequestDocument.setName(mapParams.get("name"));
            placeRequestDocument.setRadius(mapParams.get("radius"));
            placeRequestDocument.setRankedBy(mapParams.get("rankedBy"));
            placeRequestDocument.setType(mapParams.get("type"));
        }

        return placeRequestDocument;
    }
}
