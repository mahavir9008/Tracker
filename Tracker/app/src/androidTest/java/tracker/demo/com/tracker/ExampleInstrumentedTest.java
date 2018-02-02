package tracker.demo.com.tracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public final ActivityTestRule<MapsActivity> rule = new ActivityTestRule<>(MapsActivity.class);
    private MockLocationProvider mock;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals("tracker.demo.com.tracker", appContext.getPackageName());
    }

    @Test
    public void testLocationService() {
        final MapsActivity activity = rule.getActivity();
        mock = new MockLocationProvider(activity);

        //Set test location
        mock.pushLocation(-12.34);

        LocationManager locMgr = (LocationManager)
                activity.getSystemService(LOCATION_SERVICE);
        LocationListener lis = new LocationListener(LocationManager.NETWORK_PROVIDER) {
            public void onLocationChanged(Location location) {
                //You will get the mock location
                mock.shutdown();
                TestCase.assertEquals(-12.34, location.getLatitude());
                TestCase.assertEquals(-12.34, location.getLongitude());
            }

        };
        try {
            locMgr.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 1, lis);
        } catch (SecurityException e) {
            e.printStackTrace();

        }
    }

}
