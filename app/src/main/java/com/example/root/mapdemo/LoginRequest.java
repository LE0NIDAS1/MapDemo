package com.example.root.mapdemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by root on 05/09/16.
 */
public class LoginRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL ="https://192.168.1.121:8443/SpringMVC/api/allUsers"; //"http://leoeguia.net16.net/Login.php";
    private Map<String, String> params;

    public LoginRequest(Response.Listener<String> listener){
        super(Method.GET, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();


    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
