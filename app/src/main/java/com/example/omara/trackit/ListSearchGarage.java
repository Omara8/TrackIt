package com.example.omara.trackit;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 14/04/2017.
 */

public class ListSearchGarage extends StringRequest {
    private static final String LIST_SEARCH_GARAGE_URL = "http://trackitfinal.hostei.com/searchGarage.php";
    private Map<String , String> params;
    public ListSearchGarage(String company, Response.Listener<String> listener)
    {
        super(Request.Method.POST,LIST_SEARCH_GARAGE_URL,listener,null);
        params=new HashMap<>();
        params.put("company",company);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
