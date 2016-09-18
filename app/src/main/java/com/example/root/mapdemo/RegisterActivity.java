package com.example.root.mapdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText edName = (EditText) findViewById(R.id.editName);
        final EditText edMail = (EditText) findViewById(R.id.editMail);
        final EditText edPass = (EditText) findViewById(R.id.editPass);

        final Button bRegister = (Button) findViewById(R.id.BRegister);

        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                final String name = edName.getText().toString();
                final String mail = edMail.getText().toString();
                final String pass = edPass.getText().toString();


                if (!validate()) {
                    validate();
                    return;
                }

                //POP UP de registro en progreso
                final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){

                                Toast msj = Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG);
                                msj.show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                                //quitar dialogo de autenticacion
                                progressDialog.dismiss();

                            }else{
                                AlertDialog.Builder  builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                RegisterRequest registerRequest = new RegisterRequest(name, mail, pass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
            public boolean validate() {
                boolean valid = true;

                String name = edName.getText().toString();
                String email = edMail.getText().toString();
                String password = edPass.getText().toString();

                if (name.isEmpty() || name.length() < 3) {
                    edName.setError("at least 3 characters");
                    valid = false;
                } else {
                    edName.setError(null);
                }

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edMail.setError("enter a valid email address");
                    valid = false;
                } else {
                    edMail.setError(null);
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    edPass.setError("between 4 and 10 alphanumeric characters");
                    valid = false;
                } else {
                    edPass.setError(null);
                }

                return valid;
            }

        });
    }
}
