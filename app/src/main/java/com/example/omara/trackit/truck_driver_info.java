package com.example.omara.trackit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class truck_driver_info extends AppCompatActivity {
    TextView truck_info,driver_info;
    private Bitmap bmp;
    ImageView IV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_driver_info);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        final String cmp=b.getString("company");
        final String number=b.getString("number");
        truck_info=(TextView) findViewById(R.id.textView33);
        driver_info=(TextView) findViewById(R.id.textView32);
        IV=(ImageView) findViewById(R.id.imageView4);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    String plate=jsonResponse.getString("plate");
                    String role=jsonResponse.getString("role");

                    String name=jsonResponse.getString("name");
                    String email=jsonResponse.getString("email");
                    String mobile=jsonResponse.getString("mobile");
                    String address=jsonResponse.getString("address");
                    String ssn=jsonResponse.getString("ssn");
                    String dln=jsonResponse.getString("dln");
                    String image=jsonResponse.getString("image");
                    byte[] qrimage = Base64.decode(image.getBytes(),0);
                    bmp = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);

                    if (success)
                    {
                        truck_info.setText("Truck: "+number+", Plates: "+plate+", Role: "+role);
                        driver_info.setText("Name: "+name+", Email: "+email+", Mobile: "+mobile+", Address: "+address+", Social Security Number: "+ssn+", Driver License Number: "+dln);
                        IV.setImageBitmap(bmp);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(truck_driver_info.this);
                        builder.setMessage("Loading Information Failed...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        TruckDriverInfo truckDriverInfo =new TruckDriverInfo(cmp,number,responseListener2);
        RequestQueue queue= Volley.newRequestQueue(truck_driver_info.this);
        queue.add(truckDriverInfo);
    }
}
