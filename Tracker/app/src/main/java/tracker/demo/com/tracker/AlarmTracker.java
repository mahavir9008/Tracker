package tracker.demo.com.tracker;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.IBinder;

import static tracker.demo.com.tracker.Constant.LOCATION_MIN_DISTANCE;
import static tracker.demo.com.tracker.Constant.REFRESH_INTERVAL;


public class AlarmTracker extends Service {

    public static LocationManager mLocationManager = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // show notification on screen and run the service on foreground to avoid standby
        startForeground(337,  ForegroundNotification.run(this));

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        int LOCATION_INTERVAL = 1000 * (REFRESH_INTERVAL - REFRESH_INTERVAL % 3) / 3;

        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_MIN_DISTANCE, mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            ex.printStackTrace();
        }
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_MIN_DISTANCE, mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            ex.printStackTrace();
        }
        tracker.demo.com.tracker.LocationUpdateService.scheduleExactAlarm(AlarmTracker.this, (AlarmManager) getSystemService(ALARM_SERVICE), REFRESH_INTERVAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tracker.demo.com.tracker.LocationUpdateService.cancelAlarm(this, (AlarmManager) getSystemService(ALARM_SERVICE));
        if (mLocationManager != null) {
            for (LocationListener mLocationListener : mLocationListeners) {
                try {
                    mLocationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void initializeLocationManager() {
        GpsListener checkSatellite = new GpsListener();
        try {
            if (mLocationManager == null) {
                mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.addGpsStatusListener(checkSatellite);

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private class GpsListener implements GpsStatus.Listener {
        @Override
        public void onGpsStatusChanged(int event) {
        }
    }

    private final LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
