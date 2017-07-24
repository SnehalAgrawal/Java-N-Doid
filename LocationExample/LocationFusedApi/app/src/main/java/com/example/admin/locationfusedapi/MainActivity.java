package com.example.admin.locationfusedapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    private TextView lblLocation;
    private Button btnStartLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblLocation = (TextView) findViewById(R.id.lblLocation);
        Button btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        btnStartLocationUpdates = (Button) findViewById(R.id.btnLocationUpdates);
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                displayLocation();
            }
        });
        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                togglePeriodicLocationUpdates();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @SuppressLint("SetTextI18n")
    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            lblLocation.setText(latitude + ", " + longitude);
        } else {
            lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    private void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            btnStartLocationUpdates.setText(getString(R.string.btn_stop_location_updates));
            mRequestingLocationUpdates = true;
            startLocationUpdates();
            Log.d(TAG, "Periodic location updates started!");
        } else {
            btnStartLocationUpdates.setText(getString(R.string.btn_start_location_updates));
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        int UPDATE_INTERVAL = 10000;
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        int FATEST_INTERVAL = 5000;
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        int DISPLACEMENT = 10;
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(resultCode)) {
                googleAPI.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        displayLocation();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(getApplicationContext(), "Location changed!", Toast.LENGTH_SHORT).show();
        displayLocation();
    }
}
