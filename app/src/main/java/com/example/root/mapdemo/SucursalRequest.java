package com.example.root.mapdemo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.root.mapdemo.utils.SearchFilter;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;

/**
 * Created by leoeg on 24/11/2016.
 */

public class SucursalRequest extends StringRequest {
    private static final String SUCURSAL_REQUEST_URL = URL + "/api/allOffices";;
    private Map<String, String> params;
//    private Map<String, String> header;

    public SucursalRequest(Response.Listener<String> listener){
        super(Method.GET, SUCURSAL_REQUEST_URL,listener, null);
        params = new HashMap<>();

    }
}
