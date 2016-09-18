package com.example.root.mapdemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 05/09/16.
 */
public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://leoeguia.net16.net/Login.php";
    private Map<String, String> params;

    public LoginRequest(String mail, String pass, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("mail", mail);
        params.put("pass", pass);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
