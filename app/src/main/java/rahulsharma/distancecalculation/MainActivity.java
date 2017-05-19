package rahulsharma.distancecalculation;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import rahulsharma.distancecalculationlibrary.TestActivity;

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    TextView previousLatLngTv, currentLatLngTv, distanceCalculationTv;
    Button startCalculationBtn;
    String previousLatLngStr, currentLatLngTvStr, distanceCalculationStr;
    Double previousLatLngDbl, currentLatLngDbl, distanceCalculationDbl;
    Location currentLocation, previousLocation;

    GoogleApiClient googleApiClient;

    private static final long INTERVAL = 1000*10;
    private static final long FASTEST_INTERVAL = 1000*5;

    double finalDistance = 0.0;

    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    LocationRequest mLocationRequest;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();

        initilizeUI();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        startCalculationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initilizeUI() {
        previousLatLngTv = (TextView) findViewById(R.id.previous_latlng_txt);
        currentLatLngTv = (TextView) findViewById(R.id.current_latlng_txt);
        distanceCalculationTv = (TextView) findViewById(R.id.distance_txt);
        startCalculationBtn = (Button) findViewById(R.id.start_btn);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        googleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, mLocationRequest, this);
        Log.e("Location update start", pendingResult.toString());
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        double currentLat = currentLocation.getLatitude();
        double currentLng = currentLocation.getLongitude();
        double speed = location.getSpeed();
        double accuracy = location.getAccuracy();
        double altitude = location.getAltitude();

        if (!AppPrefrence.getPreviousLocLat(this).equalsIgnoreCase(currentLat + "") && !AppPrefrence.getPreviousLocLng(this).equalsIgnoreCase(currentLng + "") && currentLat != 0 && currentLng != 0) {
            AppPrefrence.setPreviousLocLat(this, currentLat + "");
            AppPrefrence.setPreviousLocLng(this, currentLng + "");
            Log.e("Previous Lat Lng", currentLat + "" + "," + currentLng + "");

            if (speed <= 80) {
                AppPrefrence.setPreviousSpeed(this, speed + "");
                Log.e("set speed", speed + "");
            }
        }

        calculateDistance(currentLocation);
    }

    private void calculateDistance(Location currentLocation) {

        double previousLat = Double.parseDouble(AppPrefrence.getPreviousLocLat(this));
        double previousLng = Double.parseDouble(AppPrefrence.getPreviousLocLng(this));
        double currentLat = currentLocation.getLatitude();
        double currentLng = currentLocation.getLongitude();

        LatLng previousLatLng = new LatLng(previousLat, previousLng);
        LatLng currentLatLng = new LatLng(currentLat, currentLng);

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(previousLat);
        startPoint.setLongitude(previousLng);

        Location endPoint=new Location("locationB");
        endPoint.setLatitude(currentLat);
        endPoint.setLongitude(currentLng);

        double p2pdistance=startPoint.distanceTo(endPoint);
        Log.e("Point 2 point distance", p2pdistance + "");
        double previousP2PDistance = Double.parseDouble(AppPrefrence.getPreviousPointDistance(this));

        double finalP2PDistance = Math.abs(previousP2PDistance - p2pdistance);
        if (finalP2PDistance <= 15 && finalP2PDistance >= 0) {

            AppPrefrence.setPreviousPointDistance(this, p2pdistance + "");
            finalDistance = Double.parseDouble(AppPrefrence.getPreviousDistance(this));
            finalDistance = finalDistance + p2pdistance;
            AppPrefrence.setPreviousDistance(this, finalDistance + "");
            distanceCalculationTv.setText(finalDistance + "");

            previousLatLngTv.setText(previousLatLng + "");
            currentLatLngTv.setText(currentLatLng + "");

            Log.e("Distance is:", p2pdistance + "");
        } else {
            Log.e("more tn 15 or less tn 5", finalP2PDistance + "");
        }
    }
}
