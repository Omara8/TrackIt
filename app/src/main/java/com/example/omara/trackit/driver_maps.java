package com.example.omara.trackit;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;


public class driver_maps extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
        , GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mGoogleMap;
    String latitude,longitude,cmp,ID;
    double mylat,mylng;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(GoogleServiceAvailable())
        {
            //Toast.makeText(this,"Perfect!!!",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_driver_maps);
            Intent test=getIntent();
            latitude=test.getStringExtra("lat");
            longitude=test.getStringExtra("lng");
            cmp=test.getStringExtra("company");
            ID=test.getStringExtra("driverID");
            googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            locationRequest = new LocationRequest();
            // locationRequest.setInterval(60 * 1000);
            locationRequest.setInterval(20* 1000);
            locationRequest.setFastestInterval(15 * 1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            ///// we  can use locationrequest.high_power for gps
            initMap();
        }
        else
        {
            setContentView(R.layout.error);
        }
    }
    public boolean GoogleServiceAvailable()
    {
        GoogleApiAvailability api=GoogleApiAvailability.getInstance();
        int isAvailable=api.isGooglePlayServicesAvailable(this);
        if (isAvailable== ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if (api.isUserResolvableError(isAvailable))
        {
            Dialog dialog=api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"Cant connect to play services",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    private void initMap() {
        MapFragment mapFragment= (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        goToLocation(Double.parseDouble(latitude), Double.parseDouble(longitude),15);
        /*mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point)
            {
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude,point.longitude)).title("New Marker."));
            }
        });*/
    }

    public void goToLocation(double lat, double lng, float zoom) {
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mGoogleMap.moveCamera(update);
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Destination."));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {

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
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mylat=location.getLatitude();
        mylng=location.getLongitude();
        Toast.makeText(getApplicationContext(), "Latitude "+mylat+"         Longitude "+mylng, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "Location changed...", Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(driver_maps.this);
                        builder.setMessage("Updating Location Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        UpdateLocation updateLocation=new UpdateLocation(cmp,ID,mylat,mylng,responseListener);
        RequestQueue queue = Volley.newRequestQueue(driver_maps.this);
        queue.add(updateLocation);
    }
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(googleApiClient.isConnected())
        {
            requestLocationUpdates();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        if(googleApiClient.isConnected())
        {
            requestLocationUpdates();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }
}