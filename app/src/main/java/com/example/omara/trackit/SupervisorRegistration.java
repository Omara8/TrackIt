package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FAST on 2/24/2017.
 */

public class SupervisorRegistration extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://trackitfinal.hostei.com/registerSuper.php";
    private Map<String , String> params;
    public SupervisorRegistration(String username , String password , String name , String email  , int mobile , String company , String address ,
                                  int SSN ,String rImage_encoded, Response.Listener<String> listener)
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
        params.put("image",rImage_encoded);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
