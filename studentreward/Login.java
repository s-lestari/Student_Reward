package com.example.studentreward;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    validateLogin(username, password);
                }
            }
        });
    }

    private void validateLogin(final String username, final String password) {
        String url = DBContract.urlLogin;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");

                            if (success) {
                                String role = jsonResponse.getString("role");
                                int userId = jsonResponse.getInt("user_id");

                                SharedPreferences sharedPreferences = getSharedPreferences("StudentRewardPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("user_id", userId);
                                editor.apply();

                                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Login.this, Dashboard.class);
                                intent.putExtra("role", role);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(Login.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = (error.getMessage() != null) ? error.getMessage() : "Connection error or invalid server response.";
                        Toast.makeText(Login.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
