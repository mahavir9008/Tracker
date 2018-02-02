package tracker.demo.com.tracker;


import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

/**
 * Utility class use for save the latitude,longitude and radius in the SharedPreferenceUtils.
 * Furthermore start the service when user confirmed the destination
 */
class Utility {

    public static void openDialog(final LatLng latLng, final Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText edittext = new EditText(context);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setMessage(context.getString(R.string.radius));
        alert.setTitle(latLng.latitude + Constant.SINGLE_SPACE + latLng.longitude);

        alert.setView(edittext);

        alert.setPositiveButton(context.getString(R.string.trackme), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_LATITUDE, latLng.latitude);
                SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_LONGITUDE, latLng.longitude);
                SharedPreferenceUtils.getInstance(context).setValue(Integer.parseInt(edittext.getText().toString()));
                SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_TRACKING_START);
                // start the service
                context.startService(new Intent(context, AlarmTracker.class));
            }
        });

        alert.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });

        alert.show();
    }

    /**
     * @param remainingDistance - residue distance
     * @param context  - context of the activity
     */
    public static void isDestinationArrived(double remainingDistance, Context context) {

        // Radius is within range & Destination status is unknown then do notification & change the status to arrived
        if (remainingDistance <= SharedPreferenceUtils.getInstance(context).getIntValue() &&
                SharedPreferenceUtils.getInstance(context).getStringValue().equals(Constant.DESTINATION_TRACKING_START)) {
            //  Change  the status to DESTINATION ARRIVED
            SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_ARRIVED);

            // Send the notification to user
            sendNotification(context);

            //  Change  a status to  DESTINATION DEPARTURE
            SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_DEPARTURE);
        }

        if (remainingDistance > SharedPreferenceUtils.getInstance(context).getIntValue()
                && SharedPreferenceUtils.getInstance(context).getStringValue().equals(Constant.DESTINATION_ARRIVED)) {
            //  Change  the status to  DESTINATION DEPARTURE
            SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_DEPARTURE);

            sendNotification(context);
            // Send a notification to user

            //  Change  the status to  DESTINATION TRACKING_START
            SharedPreferenceUtils.getInstance(context).setValue(Constant.DESTINATION_TRACKING_START);
        }
    }

    /**
     * send notification when user arrived or depart from the destination
     * @param context - context of the activity
     */

    private static void sendNotification(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Constant.NOTIFICATION_ID, ForegroundNotification.run(context));
    }

}


