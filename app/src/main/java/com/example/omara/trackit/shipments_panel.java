package com.example.omara.trackit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class shipments_panel extends AppCompatActivity {
    Button add,search;
    int PLACE_PICKER_REQUEST=1;
    String Address,cmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipments_panel);
        add=(Button) findViewById(R.id.button3);
        search=(Button) findViewById(R.id.button4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder= new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent=builder.build(shipments_panel.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
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
                Intent shipment=new Intent(shipments_panel.this,add_shipment.class);
                shipment.putExtra("company",cmp);
                shipment.putExtra("address",Address);
                shipment.putExtra("latlng",ll+"");
                startActivity(shipment);
            }
        }
    }
}
