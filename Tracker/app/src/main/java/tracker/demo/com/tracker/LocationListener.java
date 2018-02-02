package tracker.demo.com.tracker;

import android.location.Location;
import android.os.Bundle;

class LocationListener implements android.location.LocationListener {
    private final Location mLastLocation;

    public LocationListener(String provider) {
        mLastLocation = new Location(provider);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation.set(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
