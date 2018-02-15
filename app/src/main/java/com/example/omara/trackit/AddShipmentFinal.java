package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 21/04/2017.
 */

public class AddShipmentFinal extends StringRequest {
    private static final String ADD_SHIPMENT_REQUEST_URL = "http://trackitfinal.hostei.com/addShipment.php";
    private Map<String , String> params;
    public AddShipmentFinal(String company, String destination, String lat, String lng, int truckID, int driverID, Response.Listener<String> responseListener)
    {
        super(Method.POST,ADD_SHIPMENT_REQUEST_URL,responseListener,null);
        params=new HashMap<>();
        params.put("company",company);
        params.put("truckID",truckID+"");
        params.put("driverID",driverID+"");
        params.put("lng",lng);
        params.put("lat",lat);
        params.put("destination",destination);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
