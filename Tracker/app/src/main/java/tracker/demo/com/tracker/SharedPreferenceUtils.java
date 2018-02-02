package tracker.demo.com.tracker;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils mSharedPreferenceUtils;
    private final SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mSharedPreferencesEditor;

    private SharedPreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(Constant.USER_COORDINATES_PREFERENCE, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    /**
     * Creates single instance of SharedPreferenceUtils
     *
     * @param context context of Activity or Service
     * @return Returns instance of SharedPreferenceUtils
     */
    public static synchronized SharedPreferenceUtils getInstance(Context context) {

        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new SharedPreferenceUtils(context.getApplicationContext());
        }
        return mSharedPreferenceUtils;
    }

    /**
     * Stores String value in preference
     *
     * @param value value for that key
     */
    public void setValue(String value) {
        mSharedPreferencesEditor.putString(Constant.DESTINATION_STATUS, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Stores int value in preference
     *
     * @param value value for that key
     */
    public void setValue(int value) {
        mSharedPreferencesEditor.putInt(Constant.RADIUS_RANGE, value);
        mSharedPreferencesEditor.commit();
    }

    /**
     * Stores Double value in String format in preference
     *
     * @param key   key of preference
     * @param value value for that key
     */
    public void setValue(String key, double value) {
        mSharedPreferencesEditor.putLong(key, Double.doubleToRawLongBits(value));
        mSharedPreferencesEditor.commit();
    }

    /**
     * Retrieves String value from preference
     *  */
    public String getStringValue() {
        return mSharedPreferences.getString(Constant.DESTINATION_STATUS, Constant.DESTINATION_TRACKING_START);
    }

    /**
     * Retrieves int value from preference
     *
     */
    public int getIntValue() {
        return mSharedPreferences.getInt(Constant.RADIUS_RANGE, 0);
    }

    /**
     * Retrieves long value from preference
     *  @param key key of preference
     *
     */
    public long getLongValue(String key) {
        return mSharedPreferences.getLong(key, (long) 0);
    }


}