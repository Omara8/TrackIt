package com.example.omara.trackit;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class add_truck extends AppCompatActivity {
    EditText num,pl,name;
    ImageButton reg,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_truck);
        num=(EditText) findViewById(R.id.edtText);
        pl=(EditText) findViewById(R.id.edtText2);
        name=(EditText) findViewById(R.id.edtText3);
        reg=(ImageButton) findViewById(R.id.t_register);
        back=(ImageButton) findViewById(R.id.t_back);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int aNumber= Integer.parseInt(num.getText().toString());
                final String aPlate= pl.getText().toString();
                final String aCompany= name.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success==true) {
                                Toast.makeText(getApplicationContext(), "Truck Added", Toast.LENGTH_LONG).show();
                                Intent bktopanel = new Intent(add_truck.this, supervisor_panel.class);
                                bktopanel.putExtra("company", aCompany);
                                startActivity(bktopanel);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(add_truck.this);
                                builder.setMessage("Adding Truck Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                AddTruck registerRequest= new AddTruck(aNumber,aPlate,aCompany,responseListener);
                RequestQueue queue = Volley.newRequestQueue(add_truck.this);
                queue.add(registerRequest);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
