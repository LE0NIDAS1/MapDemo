package com.example.root.mapdemo.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.root.mapdemo.entity.SearchFilter;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;

/**
 * Created by leoeg on 10/12/2016.
 */

public class PayPalRequest extends StringRequest {

    private static final String SEARCH_REQUEST_URL = URL + "/api/search";;
    private Map<String, String> params;

    public PayPalRequest(SearchFilter search, Response.Listener<String> listener){
        super(Method.POST, SEARCH_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("beginDate",search.getBeginDate());
        params.put("airConditioner", "true");
        params.put("endDate", search.getEndDate());
        params.put("passangers","1");
        params.put("luggage",  "0");
        params.put("officeOriginId", String.valueOf(search.getOfficeOriginId()));
        params.put("officeEndId", String.valueOf(search.getOfficeEndId()));

    }

    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {
        String beginDate = params.get("beginDate");
        String endDate = params.get("endDate");
        String officeOriginId = params.get("officeOriginId");
        String officeEndId = params.get("officeEndId");

        String str = "{\"airConditioner\":\"true\",\"beginDate\":\""+beginDate+"\",\"endDate\":\""+endDate+"\",\"luggage\":\"0\",\"officeEndId\":\""+officeEndId+"\",\"officeOriginId\":\""+officeOriginId+"\",\"passangers\":\"0\"}";
        return str.getBytes();
    };
    @Override
    public String getBodyContentType()
    {
        return "Content-Type, application/json; charset=utf-8";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Content-Type","application/json;charset=UTF-8");
        return params;
    }
}
