package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 18/06/2017.
 */

public class UpdateLocation extends StringRequest {
    private static final String UPDATE_LOCATION_URL = "http://trackitfinal.hostei.com/updateTruckLocation.php";
    private Map<String , String> params;
    public UpdateLocation(String company,String driverID, Double lat,Double lng, Response.Listener<String> responseListener)
    {
        super(Method.POST,UPDATE_LOCATION_URL,responseListener,null);
        params=new HashMap<>();
        params.put("company",company);
        params.put("driverID",driverID+"");
        params.put("lng",lng+"");
        params.put("lat",lat+"");
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
