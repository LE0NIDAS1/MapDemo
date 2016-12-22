package com.example.root.mapdemo.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.root.mapdemo.entity.PromotionCode;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;

/**
 * Created by leoeg on 20/12/2016.
 */

public class PromocionRequest extends StringRequest {

    private static final String PROMOCION_REQUEST_URL = URL + "/api/validatepromo";;
    private Map<String, String> params;

    public PromocionRequest(PromotionCode promotion, Response.Listener<String> listener) {
        super(Method.POST, PROMOCION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("promotionCode", promotion.getPromotionCode());
        params.put("modelId", promotion.getModelId());
        params.put("origin", promotion.getOrigin());
        params.put("destiny", promotion.getDestiny());
        params.put("originDate", promotion.getOriginDate());
    }

    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {
        String promotionCode = params.get("promotionCode");
        String modelId = params.get("modelId");
        String origin = params.get("origin");
        String destiny = params.get("destiny");
        String originDate = params.get("originDate");

        String str = "{\"promotionCode\":\""+promotionCode+"\",\"model\":{\"id\":"+modelId+"},\"origin\":"+origin+",\"destiny\":"+destiny+",\"originDate\":\""+originDate+"\"}";
        return str.getBytes();
    };

    @Override
    public String getBodyContentType() {
        return "Content-Type, application/json; charset=utf-8";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("Content-Type","application/json;charset=UTF-8");
        return params;
    }
}
