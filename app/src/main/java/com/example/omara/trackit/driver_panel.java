package com.example.omara.trackit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

public class driver_panel extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    ImageButton map,logout,begin,pause,end;
    boolean trip=false;
    String lat2,lng2;
    String lat;
    String lng;
    String ID;
    String cmp;
    int truckID;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Double mylat;
    private Double mylng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_panel);
        begin=(ImageButton) findViewById(R.id.button10);
        pause=(ImageButton) findViewById(R.id.button11);
        logout=(ImageButton) findViewById(R.id.imageButton14);
        map=(ImageButton) findViewById(R.id.imageButton13);
        end=(ImageButton) findViewById(R.id.button15);
        Intent test=getIntent();
        cmp=test.getStringExtra("company");
        ID=test.getStringExtra("driverID");
        //Toast.makeText(getApplicationContext(), "Company name is "+cmp, Toast.LENGTH_LONG).show();


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


        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            int no=jsonResponse.getInt("company_no");
                            if (success) {
                                if(jsonResponse.has("lat"))
                                lat=jsonResponse.getString("lat");
                                if(jsonResponse.has("lng"))
                                lng=jsonResponse.getString("lng");
                                if(jsonResponse.has("truckID"))
                                truckID=jsonResponse.getInt("truckID");
                                lat2=lat;
                                lng2=lng;
                                trip=true;
                                Toast.makeText(getApplicationContext(), "Trip Started... ", Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(driver_panel.this);
                                builder.setMessage("Begining Trip Failed...")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Begintrip begintrip= new Begintrip(cmp,ID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(driver_panel.this);
                queue.add(begintrip);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "Trip Paused... ", Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(driver_panel.this);
                                builder.setMessage("Pausing Trip Failed...")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Pausetrip pausetrip= new Pausetrip(cmp,ID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(driver_panel.this);
                queue.add(pausetrip);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "Trip Ended... ", Toast.LENGTH_LONG).show();
                                trip=false;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(driver_panel.this);
                                builder.setMessage("Pausing Trip Failed...")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Endtrip endtrip= new Endtrip(cmp,ID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(driver_panel.this);
                queue.add(endtrip);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(driver_panel.this,home.class);
                startActivity(i);
                finish();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trip==true)
                {
                    Intent i=new Intent(driver_panel.this,driver_maps.class);
                    i.putExtra("lat",lat2);
                    i.putExtra("lng",lng2);
                    i.putExtra("latitude",mylat);
                    i.putExtra("longitude",mylng);
                    i.putExtra("company",cmp);
                    i.putExtra("driverID",ID);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "You can only view the maps when you begin the trip...", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(driver_panel.this);
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
        RequestQueue queue = Volley.newRequestQueue(driver_panel.this);
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
