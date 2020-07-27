package com.example.fera1999.edelh.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fera1999.edelh.R;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private AsyncHttpClient cliente;
    private HomeViewModel homeViewModel;
    Spinner spinner_switch;
    ImageButton btnEncender;
    RequestParams params= new RequestParams();
    SharedPreferences sharedPreferences;

    Boolean bulbStatus = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnEncender= root.findViewById(R.id.btnEncender);
        spinner_switch=root.findViewById(R.id.spinner_switch);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        String group_id = sharedPreferences.getString("group_id","");
        cliente = new AsyncHttpClient();
        llenarSpinner(group_id);

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
                getString(R.string.ip_and_port)+"edelhome/editBulbState.php", new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  if(!bulbStatus) {
                      btnEncender.setImageResource(R.drawable.turnon);
                      bulbStatus = true;
                      Toast.makeText(getContext(), "Foco Apagado", Toast.LENGTH_SHORT).show();
                  } else {
                      btnEncender.setImageResource(R.drawable.turnoff);
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

    public void llenarSpinner(String group_id) {
        String URL= getString(R.string.ip_and_port) +"edelhome/listSwitch.php";
        params.put("group_id",group_id);
        cliente.post(URL, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    cargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Error al traer la lista de switch", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarSpinner(String respuesta){
        ArrayList<Foco>lista= new ArrayList<>();
        try {
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0;i<jsonArreglo.length();i++){
                if(i == 0){
                    String bulbState = jsonArreglo.getJSONObject(i).getString("bulb_state");
                    if (bulbState == "1"){
                        btnEncender.setImageResource(R.drawable.turnon);
                        bulbStatus = true;
                    }else {
                        btnEncender.setImageResource(R.drawable.turnoff);
                        bulbStatus = false;
                    }
                }
                Foco foco= new Foco();
                foco.setPlace(jsonArreglo.getJSONObject(i).getString("place"));
                lista.add(foco);
            }
            ArrayAdapter<Foco> focoArrayAdapter= new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getApplicationContext(), android.R.layout.simple_dropdown_item_1line, lista);
            spinner_switch.setAdapter(focoArrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}