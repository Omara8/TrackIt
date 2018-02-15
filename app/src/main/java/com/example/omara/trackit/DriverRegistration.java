package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 26/02/2017.
 */

public class DriverRegistration extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://trackitfinal.hostei.com/registerDriver.php";
    private Map<String , String> params;
    public DriverRegistration(String username , String password , String name , String email  , int mobile , String company , String address ,
                                  int SSN , int DLN,String rdImage_encoded, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("name",name);
        params.put("email",email);
        params.put("mobile",mobile+"");
        params.put("company",company);
        params.put("address",address);
        params.put("SSN",SSN+"");
        params.put("DLN",DLN+"");
        params.put("image",rdImage_encoded);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
