package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 22/05/2017.
 */

public class TruckDriverInfo extends StringRequest {
    private static final String INFO_URL = "http://trackitfinal.hostei.com/info.php";
    private Map<String , String> params;
    public TruckDriverInfo(String company,String number, Response.Listener<String> listener)
    {
        super(Method.POST,INFO_URL,listener,null);
        params=new HashMap<>();
        params.put("company",company);
        params.put("truck_no",number);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
