package tracker.demo.com.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * once device boot completed intent will fire and application will start printing coordinates
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constant.BOOT_COMPLETED.equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, AlarmTracker.class);
            context.startService(pushIntent);
        }
    }
}
