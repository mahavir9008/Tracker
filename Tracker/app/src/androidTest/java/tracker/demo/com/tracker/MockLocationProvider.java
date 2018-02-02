package tracker.demo.com.tracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

class MockLocationProvider {
  private final String providerName;
  private final Context ctx;
 
  public MockLocationProvider(Context ctx) {
    this.providerName = LocationManager.NETWORK_PROVIDER;
    this.ctx = ctx;
 
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);
    lm.addTestProvider(providerName, false, false, false, false, false, 
      true, true, 0, 5);
    lm.setTestProviderEnabled(providerName, true);
  }
 
  public void pushLocation(double lat) {
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);
 
    Location mockLocation = new Location(providerName);
    mockLocation.setLatitude(lat);
    mockLocation.setLongitude(23.45);
    mockLocation.setAltitude(0); 
    mockLocation.setTime(System.currentTimeMillis()); 
    lm.setTestProviderLocation(providerName, mockLocation);
  }
 
  public void shutdown() {
    LocationManager lm = (LocationManager) ctx.getSystemService(
      Context.LOCATION_SERVICE);
    lm.removeTestProvider(providerName);
  }
}