package com.example.root.mapdemo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;


class SucursalRequest extends StringRequest {
    private static final String SUCURSAL_REQUEST_URL = URL + "/api/allOffices";
    private Map<String, String> params;
//    private Map<String, String> header;

    SucursalRequest(Response.Listener<String> listener){
        super(Method.GET, SUCURSAL_REQUEST_URL,listener, null);
        params = new HashMap<>();

    }
}
