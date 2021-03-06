package tracker.demo.com.tracker;



class DistanceCalculator {

    /**
     * calculate the distance between two coordinates
     * @param lat1 - destination latitude
     * @param lon1 - destination longitude
     * @param lat2 - current latitude
     * @param lon2 - current longitude
     * @return - distance between them
     */

    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
