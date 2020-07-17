package com.example.fera1999.edelh;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText edtClave, edtUsuario;
    Button btnLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor userDataPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsuario=findViewById(R.id.usuario);
        edtClave=findViewById(R.id.clave);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userDataPref = sharedPreferences.edit();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }
    public void doLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.1.10:80/edelhome/doLogin.php", new Response.Listener<String>() {
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
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("password",edtClave.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
