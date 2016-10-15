package com.example.root.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.mapdemo.utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "FileTransfer";

    String url = "https://192.168.1.121:8443/SpringMVC/api/allUsers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edEmail = (EditText) findViewById(R.id.editMail);
        final EditText edPass = (EditText) findViewById(R.id.editPass);
        final Button BLogin = (Button) findViewById(R.id.BLogin);
        final TextView register = (TextView) findViewById(R.id.textRegister);

        HttpsTrustManager.allowAllSSL();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public  void onResponse(String response){
                JSONArray jsonResoponse = null;
                try {
                    jsonResoponse = new JSONArray(response);

//                    boolean success = jsonResoponse.getBoolean("success");
                    if (true){
                        JSONObject childJSONObject = jsonResoponse.getJSONObject(0);
                        String name = childJSONObject.getString("user_name");
                        edEmail.setText(name);

                        Toast msj = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG);
                        msj.show();



                        Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                        LoginActivity.this.startActivity(intent);

                        //quitar dialogo de autenticacion


                    }else {
                        AlertDialog.Builder  builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
        LoginRequest loginRequest = new LoginRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);

    }
}
