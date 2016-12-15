package com.example.root.mapdemo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.root.mapdemo.entity.Person;

import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import static com.example.root.mapdemo.utils.Constant.URL;


public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = URL + "/api/login"; //"http://leoeguia.net16.net/Login.php";
    private Map<String, String> params;

    public LoginRequest(Person person, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("passangers","1");


    }
    @Override
    public byte[] getBody() throws com.android.volley.AuthFailureError {
        String beginDate = params.get("beginDate");
        String endDate = params.get("endDate");
        String officeOriginId = params.get("officeOriginId");
        String officeEndId = params.get("officeEndId");

        String str = "{\"airConditioner\":\"true\",\"beginDate\":\""+beginDate+"\",\"endDate\":\""+endDate+"\",\"luggage\":\"0\",\"officeEndId\":\""+officeEndId+"\",\"officeOriginId\":\""+officeOriginId+"\",\"passangers\":\"0\"}";
        return str.getBytes();
    }

    @Override
    public String getBodyContentType()
    {
        return "Content-Type, application/json; charset=utf-8";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<>();
        params.put("Content-Type","application/json;charset=UTF-8");
        return params;
    }

}