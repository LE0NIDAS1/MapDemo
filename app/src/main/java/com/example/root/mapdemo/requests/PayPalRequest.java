package com.example.root.mapdemo.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.root.mapdemo.entity.BookingModel;
import com.example.root.mapdemo.entity.Reservation;
import com.example.root.mapdemo.entity.SearchFilter;

import java.util.HashMap;
import java.util.Map;

import static com.example.root.mapdemo.utils.Constant.URL;

/**
 * Created by leoeg on 10/12/2016.
 */

public class PayPalRequest extends StringRequest {

    private static final String SEARCH_REQUEST_URL = URL + "/api/book";;
    private Map<String, String> params;

    public PayPalRequest(Reservation reservation, BookingModel bookingModel, Response.Listener<String> listener){
        super(Method.POST, SEARCH_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("idModel", String.valueOf(bookingModel.getIdModel()));
        params.put("beginDate", bookingModel.getStartDate());
        params.put("endDate", bookingModel.getEndDate());
        params.put("originBranchOfficeId", String.valueOf(bookingModel.getOriginBranchOfficeId()));
        params.put("endBranchOfficeId", String.valueOf(bookingModel.getEndBranchOfficeId()));
        params.put("withInsurance", String.valueOf(bookingModel.isWithInsurance()));
        params.put("withFullTank", String.valueOf(bookingModel.isWithFullTank()));

        params.put("token", reservation.getToken());
        params.put("payerId", reservation.getPayerId());
        params.put("itemTotal", reservation.getItemTotal());
        params.put("orderTotal", reservation.getOrderTotal());
        params.put("promotionCode", reservation.getPromotionCode());
        params.put("clientId", String.valueOf(reservation.getClientId()));

    }

    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {

        String idModel = params.get("idModel");
        String beginDate = params.get("beginDate");
        String endDate = params.get("endDate");
        String officeOriginId = params.get("originBranchOfficeId");
        String officeEndId = params.get("endBranchOfficeId");

        String token = params.get("token");
        String payerId = params.get("payerId");
        String itemTotal = params.get("itemTotal");
        String orderTotal = params.get("orderTotal");
        String promotionCode = params.get("promotionCode");
        String clientId = params.get("clientId");


        String str = "{\"bookingModel\":{\"idModel\":"+idModel+",\"startDate\":\""+beginDate+"\",\"endDate\":\""+endDate+"\",\"originBranchOfficeId\":"+officeOriginId+",\"endBranchOfficeId\":"+officeEndId+",\"withInsurance\":true,\"withFullTank\":50},\"token\":\""+token+"\",\"payerId\":\"TG7J6WTAZWD2L\",\"itemTotal\":\""+itemTotal+"\",\"orderTotal\":\""+orderTotal+"\",\"extras\":[],\"promotionCode\":\"\",\"clientId\":"+clientId+"}";
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
