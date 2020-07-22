package com.example.fera1999.edelh.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ImageButton btnEncender;
    Boolean bulbStatus = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnEncender= root.findViewById(R.id.btnEncender);

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
                    String newStatus = bulbStatus.toString() +","+1;
                    String bulbStatusOnBinary = "0";
                    if(bulbStatus){
                        bulbStatusOnBinary = "1";
                    }
                    parameters.put("arduinoRequest", newStatus);
                    parameters.put("switch_id","1");
                    parameters.put("bulb_state", bulbStatusOnBinary);

                    return parameters;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
            requestQueue.add(stringRequest);
    }


}