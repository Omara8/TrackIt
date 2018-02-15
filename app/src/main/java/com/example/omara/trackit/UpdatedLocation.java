package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 18/06/2017.
 */

public class UpdatedLocation extends StringRequest {
    private static final String UPDATE_LOCATION_URL = "http://trackitfinal.hostei.com/updatingTruckLocation.php";
    private Map<String , String> params;
    public UpdatedLocation(String company,String truck , Response.Listener<String> responseListener) {
        super(Method.POST, UPDATE_LOCATION_URL, responseListener, null);
        params = new HashMap<>();
        params.put("company", company);
        params.put("truck_no", truck);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
