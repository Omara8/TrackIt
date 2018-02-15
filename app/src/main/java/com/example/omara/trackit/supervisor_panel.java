package com.example.omara.trackit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class supervisor_panel extends AppCompatActivity {
    int PLACE_PICKER_REQUEST=1;
    ArrayList<String> list2 = new ArrayList<String>();
    ImageButton logout,vGPS,cam,tadd,dest,trucks,on_Route,on_Break,in_Garage,sadd;
    String Address,cmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_panel);
        trucks=(ImageButton) findViewById(R.id.button16);
        dest=(ImageButton) findViewById(R.id.button17);
        on_Route=(ImageButton) findViewById(R.id.button18);
        on_Break=(ImageButton) findViewById(R.id.button19);
        in_Garage=(ImageButton) findViewById(R.id.button20);
        vGPS=(ImageButton) findViewById(R.id.imageButton21);
        cam=(ImageButton) findViewById(R.id.imageButton22);
        logout=(ImageButton) findViewById(R.id.imageButton23);
        tadd=(ImageButton) findViewById(R.id.imageButton24);
        sadd=(ImageButton) findViewById(R.id.imageButton4);
        Intent test=getIntent();
        cmp=test.getStringExtra("company");
        //Toast.makeText(getApplicationContext(), "Company name is "+cmp, Toast.LENGTH_LONG).show();
        sadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(supervisor_panel.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(supervisor_panel.this,home.class);
                startActivity(i);
                finish();
            }
        });
        trucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",1);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",2);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        on_Route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",3);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        on_Break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",4);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        in_Garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",5);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        vGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",6);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,lists_activity.class);
                l.putExtra("button",7);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
        tadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(supervisor_panel.this,add_truck.class);
                startActivity(l);
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                Place place= PlacePicker.getPlace(data,this);
                Address=String.format("Place: %s",place.getAddress());
                LatLng ll=place.getLatLng();
                Intent shipment=new Intent(supervisor_panel.this,add_shipment.class);
                shipment.putExtra("company",cmp);
                shipment.putExtra("address",Address);
                shipment.putExtra("latlng",ll+"");
                startActivity(shipment);
            }
        }
    }
}
