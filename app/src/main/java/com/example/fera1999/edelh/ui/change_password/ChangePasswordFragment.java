package com.example.fera1999.edelh.ui.change_password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fera1999.edelh.DialogMaker;
import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordModel galleryViewModel;
    EditText edtActualPassword, edtNewPassword, edtNewPasswordConfirm;
    SharedPreferences sharedPreferences;
    Button btnChangePassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(ChangePasswordModel.class);

        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        edtActualPassword = root.findViewById(R.id.edtActualPassword);
        edtNewPassword = root.findViewById(R.id.edtNewPassword);
        edtNewPasswordConfirm = root.findViewById(R.id.edtNewPasswordConfirm);
        btnChangePassword = root.findViewById(R.id.btnChangePassword);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        return root;
    }

    public void changePassword() {
        String newPass = edtNewPassword.getText().toString();
        String confirmPass = edtNewPasswordConfirm.getText().toString();

        if(newPass == confirmPass){

            edtNewPassword.setText("");
            edtNewPasswordConfirm.setText("");
            edtNewPassword.requestFocus();
            Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "http://192.168.1.10:80/edelhome/changePassword.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Contraseña cambiada con éxito", Toast.LENGTH_LONG).show();
                    Fragment newFragment = new HomeFragment();
                    FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, newFragment, newFragment.getTag()
                    );

                    transaction.commit();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    edtActualPassword.setText("");
                    edtNewPassword.setText("");
                    edtNewPasswordConfirm.setText("");
                    openDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    String user_id = sharedPreferences.getString("user_id", "");
                    Map<String, String> parameters = new HashMap<>();

                    parameters.put("user_id", Objects.requireNonNull(user_id));
                    parameters.put("new_pass", edtNewPassword.getText().toString());
                    parameters.put("actual_pass", edtActualPassword.getText().toString());

                    return parameters;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
    public void openDialog (){
        DialogMaker dialog = new DialogMaker();
        dialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "error");
    }

}