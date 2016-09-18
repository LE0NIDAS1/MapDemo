package com.example.root.mapdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edEmail = (EditText) findViewById(R.id.editMail);
        final EditText edPass = (EditText) findViewById(R.id.editPass);
        final Button BLogin = (Button) findViewById(R.id.BLogin);
        final TextView register = (TextView) findViewById(R.id.textRegister);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent  = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        BLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String mail = edEmail.getText().toString();
                final String pass = edPass.getText().toString();

                if (!validate()) {
                    onLoginFailed();
                    return;
                }


                //Pop up de Atenticacion en progreso
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public  void onResponse(String response){
                        JSONObject jsonResoponse = null;
                        try {
                            jsonResoponse = new JSONObject(response);

                            boolean success = jsonResoponse.getBoolean("success");
                            if (success){
                                String name = jsonResoponse.getString("name");


                                Toast msj = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG);
                                msj.show();



                                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                                LoginActivity.this.startActivity(intent);

                                //quitar dialogo de autenticacion
                                progressDialog.dismiss();


                            }else {
                                AlertDialog.Builder  builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                LoginRequest loginRequest = new LoginRequest(mail, pass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }

            public boolean validate() {
                boolean valid = true;

                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edEmail.setError("enter a valid email address");
                    valid = false;
                } else {
                    edEmail.setError(null);
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    edPass.setError("between 4 and 10 alphanumeric characters");
                    valid = false;
                } else {
                    edPass.setError(null);
                }

                return valid;
            }

            public void onLoginFailed() {
                Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
