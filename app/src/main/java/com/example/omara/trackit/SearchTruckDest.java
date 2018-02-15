package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 15/06/2017.
 */

public class SearchTruckDest extends StringRequest {
    private static final String ADD_TRUCK_REQUEST_URL = "http://trackitfinal.hostei.com/searchTruckDest.php";
    private Map<String , String> params;
    public SearchTruckDest(String company, String destination, Response.Listener<String> listener)
    {
        super(Method.POST,ADD_TRUCK_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("company",company);
        params.put("destination",destination);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
