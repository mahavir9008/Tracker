package tracker.demo.com.tracker;

import android.content.Context;
import android.support.v4.app.NotificationCompat;

class ForegroundNotification {

    static android.app.Notification run(Context context) {

        return new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.trackme))
                .setContentText(SharedPreferenceUtils.getInstance(context).getStringValue())
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
    }

}
