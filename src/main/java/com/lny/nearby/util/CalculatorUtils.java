package com.lny.nearby.util;

public class CalculatorUtils {

    /**
     * <p>This routine calculates the distance between two points (given the latitude/longitude of those points).
     * It is being used to calculate the distance between two locations using GeoDataSource (TM) products</p><br>
     * <p>Definitions: South latitudes are negative, east longitudes are positive.</p>
     * <p>Source reference: <a href="https://www.geodatasource.com/developers/java">GeoDataSource</a></p>
     *
     * @param lat1 Latitude of point 1 (in decimal degrees)
     * @param lon1 Longitude of point 1 (in decimal degrees)
     * @param lat2 Latitude of point 2 (in decimal degrees)
     * @param lon2 Longitude of point 1 (in decimal degrees)
     * @param unit The unit you desire for results: <br>
     *             <ul>
     *                <li><b>M</b> is stature miles (default)</li>
     *                 <li><b>K</b> is kilometers (default)</li>
     *                 <li><b>N</b> is nautical miles (default)</li>
     *             </ul>
     * @return a double of the distance calculated.
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equalsIgnoreCase("K")) {
                dist = dist * 1.609344;
            } else if (unit.equalsIgnoreCase("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    /**
     * A helper to rounding double values.
     *
     * @param value  a double to rounding.
     * @param places quantity of numbers to rounding.
     * @return the double rounded.
     */
    public static double roundDouble(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
