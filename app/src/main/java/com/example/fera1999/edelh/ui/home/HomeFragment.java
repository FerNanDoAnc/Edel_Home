package com.example.fera1999.edelh.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fera1999.edelh.LoginActivity;
import com.example.fera1999.edelh.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {

    private AsyncHttpClient cliente;
    private HomeViewModel homeViewModel;
    Spinner spinner_switch;
    ImageButton btnEncender;
    Boolean bulbStatus = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnEncender= root.findViewById(R.id.btnEncender);
        spinner_switch=root.findViewById(R.id.spinner_switch);


        cliente=new AsyncHttpClient();
        llenarSpinner();

        btnEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBulbStatus();
            }
        });

        return root;

    }

    public void changeBulbStatus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.1.36:80/edelhome/editBulbState.php", new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  if(!bulbStatus) {
                      btnEncender.setBackgroundResource(R.drawable.boton_encender);
                      bulbStatus = true;
                      Toast.makeText(getContext(), "Foco Apagado", Toast.LENGTH_SHORT).show();
                  } else {
                      btnEncender.setBackgroundResource(R.drawable.boton_apagar);
                      bulbStatus = false;
                      Toast.makeText(getContext(), "Foco Encendido", Toast.LENGTH_SHORT).show();
                  }
              }
        }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(
                         Objects.requireNonNull(getActivity()).getApplicationContext(),
                         error.toString(),
                         Toast.LENGTH_LONG).show();
             }
         }) {
               @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<>();
                    String bulbStatusOnBinary = "0";
                    if(bulbStatus){
                        bulbStatusOnBinary = "1";
                    }
                   String newStatus =  1 +","+ bulbStatusOnBinary;
                    parameters.put("arduinoRequest", newStatus);
                    parameters.put("switch_id","1");
                    parameters.put("bulb_state", bulbStatusOnBinary);

                    return parameters;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
            requestQueue.add(stringRequest);
    }

    public void llenarSpinner() {
        String URL="http://192.168.1.36:80/edelhome/listSwitch.php";
        cliente.post(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    private void cargarSpinner(String respuesta){
        ArrayList<Foco>lista=new ArrayList<Foco>();
        try {
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0;i<jsonArreglo.length();i++){
                Foco foco= new Foco();
                foco.setPlace(jsonArreglo.getJSONObject(i).getString("place"));
                lista.add(foco);
            }
            ArrayAdapter<Foco> focoArrayAdapter=new ArrayAdapter<Foco>(Objects.requireNonNull(getActivity()).getApplicationContext(), android.R.layout.simple_dropdown_item_1line, lista);
            spinner_switch.setAdapter(focoArrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}