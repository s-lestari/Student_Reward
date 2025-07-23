package com.example.studentreward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private RadioButton roleLecturerButton, roleStudentButton;
    private Button registerButton;
    private RequestQueue requestQueue;
    private String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        roleLecturerButton = findViewById(R.id.radioButton1);
        roleStudentButton = findViewById(R.id.radioButton2);
        registerButton = findViewById(R.id.btn_register);

        requestQueue = Volley.newRequestQueue(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(!(username.isEmpty() || password.isEmpty() || role.isEmpty())) {
                    String finalRole = role;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.urlRegister, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                            if (finalRole.equals("Student")){
                                insertPoint(username);
                            }else {
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected HashMap<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            params.put("role", finalRole);

                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked=((RadioButton) view).isChecked();
        if (checked) {
            if (view.getId() == R.id.radioButton1) {
                role = "Lecturer";
            } else if (view.getId() == R.id.radioButton2) {
                role = "Student";
            }
        }
    }

    private void insertPoint(String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBContract.urlInsertPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Point added for student", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add point", Toast.LENGTH_SHORT).show();
                    }
                    // Setelah selesai, arahkan ke Login
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username); // Kirim username ke server
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}
