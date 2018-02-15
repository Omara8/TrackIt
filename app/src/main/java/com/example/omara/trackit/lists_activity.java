package com.example.omara.trackit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class lists_activity extends AppCompatActivity {
    ListView LV;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    ArrayList<String> list3 = new ArrayList<String>();
    ArrayList<String> list4 = new ArrayList<String>();
    String[] result=new String[]{};
    String[] splitted=new String[]{};
    int button_clicked;
    double lat,latD;
    double lng,lngD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists_activity);
        LV=(ListView) findViewById(R.id.listView);
        Intent i=getIntent();
        Bundle b=i.getExtras();
        result=b.getStringArray("list");
        button_clicked=b.getInt("button");
        final String cmp=b.getString("company");
        /*ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_viewer,R.id.label,result);
        LV.setAdapter(adapter);*/
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int counti=jsonResponse.getInt("counti");
                    String truck_numbers[]=new String[counti];
                    String truck_role[]=new String[counti];
                    String number="";
                    String role="";
                    if (number!=null) {
                        for(int i=0;i<counti;i++)
                        {
                            number = jsonResponse.getString("truck_no"+i+"");
                            role=jsonResponse.getString("role"+i+"");
                            truck_numbers[i]=number;
                            truck_role[i]=role;
                            if(role.equals("garage"))
                            list.add("Truck "+number+"    In Garage");
                            else if (role.equals("on_route"))
                                list.add("Truck "+number+"    On Route");
                            else if (role.equals("on_pause"))
                                list.add("Truck "+number+"    On Break");
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_viewer,R.id.label,list);
                        adapter.notifyDataSetChanged();
                        LV.setAdapter(adapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(lists_activity.this);
                        builder.setMessage("Loading Trucks Failed...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        if(button_clicked==1) {
            ListSearch truckSearch = new ListSearch(cmp, responseListener);
            RequestQueue queue = Volley.newRequestQueue(lists_activity.this);
            queue.add(truckSearch);

        }
        else if(button_clicked==2)
        {
            Response.Listener<String> responseListener3 =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int counti=jsonResponse.getInt("counti");
                    boolean success=jsonResponse.getBoolean("success");
                    String dest="";
                    if (success) {
                        for(int i=0;i<counti;i++)
                        {
                            dest=jsonResponse.getString("destination"+i+"");
                            list3.add(dest);
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_viewer,R.id.label,list3);
                        adapter.notifyDataSetChanged();
                        LV.setAdapter(adapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(lists_activity.this);
                        builder.setMessage("Loading Destinations Failed...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
            SearchDest searchDest=new SearchDest(cmp,responseListener3);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(searchDest);
        }
        else if(button_clicked==3)
        {
            ListSearchRoute truckRouteSearch = new ListSearchRoute(cmp,responseListener);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(truckRouteSearch);
        }
        else if(button_clicked==4)
        {
            ListSearchPause truckPauseSearch = new ListSearchPause(cmp,responseListener);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(truckPauseSearch);
        }
        else if(button_clicked==5)
        {
            ListSearchGarage truckGarageSearch = new ListSearchGarage(cmp,responseListener);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(truckGarageSearch);
        }
        else if(button_clicked==6)
        {
            ListSearchPauseOnRoute truckPauseOnRouteSearch = new ListSearchPauseOnRoute(cmp,responseListener);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(truckPauseOnRouteSearch);
        }
        else if(button_clicked==7)
        {
            ListSearchPauseOnRoute truckPauseOnRouteSearch = new ListSearchPauseOnRoute(cmp,responseListener);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(truckPauseOnRouteSearch);
        }
        else if (button_clicked==8)
        {
            Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        int counti=jsonResponse.getInt("counti");
                        String ID,dest;
                        String number=String.valueOf(counti);
                        if (number!=null) {
                            for(int i=0;i<counti;i++)
                            {
                                ID=jsonResponse.getString("truck_no"+i);
                                dest=jsonResponse.getString("destination"+i);
                                list2.add("Truck Number:"+ID+"    Destination:"+dest);
                            }
                            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_viewer,R.id.label,list2);
                            adapter.notifyDataSetChanged();
                            LV.setAdapter(adapter);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(lists_activity.this);
                            builder.setMessage("Loading Shipments Failed...")
                                    .setNegativeButton("Retry",null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            SearchShipment searchShipment=new SearchShipment(cmp,responseListener2);
            RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
            queue.add(searchShipment);
        }
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (button_clicked==1)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    splitted=selectedFromList.split(" ");
                    Intent i3=new Intent(lists_activity.this,truck_driver_info.class);
                    i3.putExtra("number",splitted[1]);
                    i3.putExtra("company",cmp);
                    startActivity(i3);
                }
                else if(button_clicked==2)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    Response.Listener<String> responseListener4 =new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                int counti=jsonResponse.getInt("counti");
                                boolean success=jsonResponse.getBoolean("success");
                                String truck="";
                                String done="";
                                if (success) {
                                    for(int i=0;i<counti;i++)
                                    {
                                        truck=jsonResponse.getString("truck_no"+i+"");
                                        done=jsonResponse.getString("done"+i+"");
                                        list4.add("Truck Number : "+truck+"    Finished : "+done);
                                    }
                                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_viewer,R.id.label,list4);
                                    adapter.notifyDataSetChanged();
                                    LV.setAdapter(adapter);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(lists_activity.this);
                                    builder.setMessage("Loading Trucks Failed...")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SearchTruckDest searchTruckDest=new SearchTruckDest(cmp,selectedFromList,responseListener4);
                    RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
                    queue.add(searchTruckDest);
                    /*splitted=selectedFromList.split(" ");
                    Intent i3=new Intent(lists_activity.this,truck_driver_info.class);
                    i3.putExtra("number",splitted[1]);
                    i3.putExtra("company",cmp);
                    startActivity(i3);*/
                }
                else if(button_clicked==3)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    splitted=selectedFromList.split(" ");
                    Intent i3=new Intent(lists_activity.this,truck_driver_info.class);
                    i3.putExtra("number",splitted[1]);
                    i3.putExtra("company",cmp);
                    startActivity(i3);
                }
                else if(button_clicked==4)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    splitted=selectedFromList.split(" ");
                    Intent i3=new Intent(lists_activity.this,truck_driver_info.class);
                    i3.putExtra("number",splitted[1]);
                    i3.putExtra("company",cmp);
                    startActivity(i3);
                }
                else if(button_clicked==5)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    splitted=selectedFromList.split(" ");
                    Intent i3=new Intent(lists_activity.this,truck_driver_info.class);
                    i3.putExtra("number",splitted[1]);
                    i3.putExtra("company",cmp);
                    startActivity(i3);
                }
                else if(button_clicked==6)
                {
                    String selectedFromList = (String) LV.getItemAtPosition(i);
                    splitted=selectedFromList.split(" ");
                    Response.Listener<String> responseListener4 =new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success=jsonResponse.getBoolean("success");
                                if (success) {
                                    lat= jsonResponse.getDouble("lat");
                                    lng= jsonResponse.getDouble("lng");
                                    latD=jsonResponse.getDouble("latD");
                                    lngD=jsonResponse.getDouble("lngD");
                                    Intent i2=new Intent(lists_activity.this,supervisor_maps.class);
                                    i2.putExtra("lat",lat);
                                    i2.putExtra("lng",lng);
                                    i2.putExtra("latD",latD);
                                    i2.putExtra("lngD",lngD);
                                    startActivity(i2);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(lists_activity.this);
                                    builder.setMessage("Loading Trucks Failed...")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    UpdatedLocation updatedLocation=new UpdatedLocation(cmp,splitted[1],responseListener4);
                    RequestQueue queue=Volley.newRequestQueue(lists_activity.this);
                    queue.add(updatedLocation);
                }
                else if(button_clicked==7)
                {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.rcreations.ipcamviewerBasic");
                    if (intent != null) {
                        // We found the activity now start the activity
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        // Bring user to the market or let them choose an app?
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse("market://details?id=" + "com.rcreations.ipcamviewerBasic"));
                        startActivity(intent);
                    }
                    /*Intent cv=new Intent(Intent.ACTION_VIEW);
                    cv.setData(Uri.parse("http://192.168.2.10:99/index.htm"));
                    startActivity(cv);*/
                }
            }
        });
    }
}
