package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 25/04/2017.
 */

public class Pausetrip extends StringRequest {
    private static final String BEGIN_TRIP_REQUEST = "http://trackitfinal.hostei.com/pauseTrip.php";
    private Map<String , String> params;
    public Pausetrip(String company,String driverID, Response.Listener<String> listener)
    {
        super(Method.POST,BEGIN_TRIP_REQUEST,listener,null);
        params=new HashMap<>();
        params.put("company",company);
        params.put("driverID",driverID+"");
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
