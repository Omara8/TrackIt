package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 06/03/2017.
 */

public class AddTruck extends StringRequest {
    private static final String ADD_TRUCK_REQUEST_URL = "http://trackitfinal.hostei.com/registerTruck.php";
    private Map<String , String> params;
    public AddTruck(int number , String plate , String company , Response.Listener<String> listener)
    {
        super(Method.POST,ADD_TRUCK_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("number",number+"");
        params.put("plate",plate);
        params.put("company",company);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
