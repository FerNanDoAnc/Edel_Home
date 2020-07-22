package com.example.fera1999.edelh;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {

    EditText edtPass, edtUser;
    Button btnLogin;
    TextView txtNewUser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor userDataPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userDataPref = sharedPreferences.edit();
        activityGuard();
        setContentView(R.layout.activity_login);
        edtUser =findViewById(R.id.edtUser);
        edtPass =findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtNewUser = findViewById(R.id.txtNewuser);

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,
                        "Comunícate con el administrador para gestionar tus datos de acceso.",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        activityGuard();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        activityGuard();
    }

    private void activityGuard() {
        boolean isLogged = sharedPreferences.getBoolean("isLogged",false);
        if(isLogged){
            Intent intent = new Intent(getApplicationContext(), MenuDrawerActivity.class);
            startActivity(intent);
        }
    }
    public void doLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.1.21:80/edelhome/doLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), MenuDrawerActivity.class);
                   try {
                        JSONObject userDataJson = new JSONObject(response);
                        userDataPref.putString("user_id", userDataJson.get("user_id").toString());
                        userDataPref.putString("username", userDataJson.get("username").toString());
                        userDataPref.putString("email", userDataJson.get("email").toString());
                        userDataPref.putString("isAdministrator", userDataJson.get("administrador").toString());
                        userDataPref.putString("group_id", userDataJson.get("group_id").toString());
                        userDataPref.putBoolean("isLogged",true);
                        userDataPref.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("usuario", edtUser.getText().toString());
                parametros.put("password", edtPass.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
