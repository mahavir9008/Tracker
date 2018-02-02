package tracker.demo.com.tracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;

public class LocationUpdateService extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        scheduleExactAlarm(context, (AlarmManager) context.getSystemService(Context.ALARM_SERVICE), intent.getIntExtra(Constant.INTERVAL, Constant.REFRESH_INTERVAL));

        Handler handler = new Handler();
        Runnable periodicUpdate = new Runnable() {
            @Override
            public void run() {
                // record the latest locations from both gps and network if possible
                Location location = null;
                try {
                    if (AlarmTracker.mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        location = AlarmTracker.mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    } else {
                        if (AlarmTracker.mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                            location = AlarmTracker.mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    if (location != null) {
                        Double remainingDistance = DistanceCalculator.distance(Double.longBitsToDouble(SharedPreferenceUtils.getInstance(context).getLongValue(Constant.DESTINATION_LATITUDE)), Double.longBitsToDouble(SharedPreferenceUtils.getInstance(context).getLongValue(Constant.DESTINATION_LONGITUDE)), location.getLatitude(), location.getLongitude());
                        Utility.isDestinationArrived(remainingDistance, context);
                    }

                } catch (SecurityException e) {
                    e.printStackTrace();
                }

            }
        };

        handler.post(periodicUpdate);
    }

    public static void scheduleExactAlarm(Context context, AlarmManager alarms, int interval) {
        Intent i = new Intent(context, LocationUpdateService.class).putExtra(Constant.INTERVAL, interval);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        alarms.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval * 1000 - SystemClock.elapsedRealtime() % 1000, pi);
    }

    public static void cancelAlarm(Context context, AlarmManager alarms) {
        Intent i = new Intent(context, LocationUpdateService.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        alarms.cancel(pi);
    }


}
