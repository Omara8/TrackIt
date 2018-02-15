package com.example.omara.trackit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class add_shipment extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    TextView info,info2;
    Spinner trucks,drivers;
    ImageButton button,search;
    String[] tid,did;
    String id1,id2;
    ArrayAdapter<String> adapter,adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipment);
        Intent test=getIntent();
        final String cmp=test.getStringExtra("company");
        final String address=test.getStringExtra("address").substring(7,test.getStringExtra("address").length());
        String LatLng=test.getStringExtra("latlng");
        String latlong=LatLng.substring(10,LatLng.length()-1);
        String[] loc=latlong.split(",");
        final String Lat=loc[0];
        final String Lng=loc[1];
        info=(TextView) findViewById(R.id.textView10);
        info2=(TextView) findViewById(R.id.textView22);
        button=(ImageButton) findViewById(R.id.imageButton5);
        search=(ImageButton) findViewById(R.id.imageButton6);
        info.setText(Lat+"/"+Lng);
        info2.setText(address);
        trucks=(Spinner) findViewById(R.id.spinner2);
        drivers=(Spinner) findViewById(R.id.driver_spinner);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int counti=jsonResponse.getInt("counti");
                    int counti2=jsonResponse.getInt("counti2");
                    String truck_numbers[]=new String[counti];
                    String driver_ids[]=new String[counti2] ;
                    String truck_id[]=new String[counti];
                    String truckid="";
                    String number="";
                    String ids="";
                    String name="";

                    if (number!=null) {
                        for(int i=0;i<counti;i++)
                        {
                            number = jsonResponse.getString("truck_no"+i+"");
                            truckid=jsonResponse.getString("truckID"+i+"");
                            truck_id[i]=truckid;
                            truck_numbers[i]=number;
                            list.add("ID:"+truckid+"    Number:"+number);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(add_shipment.this);
                        builder.setMessage("Loading Trucks Failed...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                    if (ids!=null) {
                        for(int i=0;i<counti2;i++)
                        {
                            ids = jsonResponse.getString("driverID"+i+"");
                            name=jsonResponse.getString("name"+i+"");
                            driver_ids[i]=ids;
                            list2.add("ID:"+ids+"    Name:"+name);
                        }
                        adapter2.notifyDataSetChanged();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(add_shipment.this);
                        builder.setMessage("Loading Drivers Failed...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        AddShipment shipmentRequest= new AddShipment(cmp, responseListener);
        RequestQueue queue = Volley.newRequestQueue(add_shipment.this);
        queue.add(shipmentRequest);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);
        adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list2);
        trucks.setAdapter(adapter);
        drivers.setAdapter(adapter2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tid = trucks.getSelectedItem().toString().split(" ");
                did = drivers.getSelectedItem().toString().split(" ");
                id1 = tid[0].substring(3, tid[0].length());
                id2 = did[0].substring(3, tid[0].length());
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent bktopanel = new Intent(add_shipment.this, supervisor_panel.class);
                                bktopanel.putExtra("company",cmp);
                                startActivity(bktopanel);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(add_shipment.this);
                                builder.setMessage("Adding Shipment Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddShipmentFinal shipmentRequest= new AddShipmentFinal(cmp,address,Lat,Lng,Integer.parseInt(id1),Integer.parseInt(id2), responseListener);
                RequestQueue queue = Volley.newRequestQueue(add_shipment.this);
                queue.add(shipmentRequest);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(add_shipment.this,lists_activity.class);
                l.putExtra("button",8);
                l.putExtra("company",cmp);
                startActivity(l);
            }
        });
    }
}
