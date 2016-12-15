package com.example.root.mapdemo.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;


public class ExtraRequest extends StringRequest {
    private static final String EXTRA_REQUEST_URL = URL + "/api/allExtras";
    private Map<String, String> params;
//    private Map<String, String> header;

    public ExtraRequest(Response.Listener<String> listener){
        super(Method.GET, EXTRA_REQUEST_URL,listener, null);
        params = new HashMap<>();
    }
}
