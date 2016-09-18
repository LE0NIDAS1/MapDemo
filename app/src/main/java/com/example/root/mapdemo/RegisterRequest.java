package com.example.root.mapdemo;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://leoeguia.net16.net/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String mail, String pass, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("mail", mail);
        params.put("pass", pass);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
