package tracker.demo.com.tracker;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

class CheckPermission {
    /**
     * Checks if the app has permission to get the location
     * If the app does not has permission then the user will be prompted to grant permissions
     * @param activity - instance of the activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.REQUEST_FINE_LOCATION);
    }
}
