package com.example.omara.trackit;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Omara on 25/02/2017.
 */

public class SupervisorLogin extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://trackitfinal.hostei.com/loginSuper.php";
    private Map<String , String> params;
    public SupervisorLogin(String username , String password, Response.Listener<String> listener)
    {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
