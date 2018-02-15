package com.example.omara.trackit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {
    Spinner dropdown;
    EditText un,pw;
    ImageButton logi,reg,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        statusCheck();
        dropdown = (Spinner)findViewById(R.id.spinner);
        String[] items = new String[]{"Supervisor", "Driver"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        final ProgressDialog pd  = new ProgressDialog(home.this);
        dropdown.setAdapter(adapter);
        un=(EditText) findViewById(R.id.editText);
        pw=(EditText) findViewById(R.id.editText2);
        logi=(ImageButton) findViewById(R.id.imageButton);
        reg=(ImageButton) findViewById(R.id.imageButton2);
        exit=(ImageButton) findViewById(R.id.imageButton3);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        logi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = un.getText().toString();
                final String password = pw.getText().toString();

                pd.setCancelable(true);
                pd.setMessage("Please Wait .... ");
                pd.show();
                if(dropdown.getSelectedItem().toString()=="Supervisor")
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String company = jsonResponse.getString("company");

                                    Intent intent = new Intent(home.this, supervisor_panel.class);
                                    intent.putExtra("company", company);
                                    pd.dismiss();
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SupervisorLogin loginRequest = new SupervisorLogin(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(home.this);
                    queue.add(loginRequest);
                }
                else if (dropdown.getSelectedItem().toString()=="Driver")
                {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String company = jsonResponse.getString("company");
                                    int ID=jsonResponse.getInt("driverID");
                                    Intent intent = new Intent(home.this, driver_panel.class);
                                    intent.putExtra("company", company);
                                    intent.putExtra("driverID",String.valueOf(ID));
                                    pd.dismiss();
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(home.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DriverLogin loginRequest = new DriverLogin(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(home.this);
                    queue.add(loginRequest);
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dropdown.getSelectedItem().toString()=="Supervisor")
                {
                    Intent reg1=new Intent(home.this,supervisor_registration.class);
                    startActivity(reg1);
                }
                else if (dropdown.getSelectedItem().toString()=="Driver")
                {
                    Intent reg2=new Intent(home.this,driver_registration.class);
                    startActivity(reg2);
                }
            }
        });
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
