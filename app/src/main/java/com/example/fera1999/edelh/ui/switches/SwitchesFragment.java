package com.example.fera1999.edelh.ui.switches;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.R.*;
import com.example.fera1999.edelh.adaptadores.AdaptadorSwitches;
import com.example.fera1999.edelh.adaptadores.AdaptadorUsuario;
import com.example.fera1999.edelh.clases.Switches;
import com.example.fera1999.edelh.clases.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SwitchesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String newUserName, group_id;
    int idusername=0;
    private SwitchesViewModel galleryViewModel;
    ListView listView;
    ArrayList<Switches> lista;
    AdaptadorSwitches adaptador = null;


    ArrayList<Usuario> listausuario;
    AdaptadorUsuario adaptadorusuario = null;

    EditText edtNombreUsuario, edtCorreoEletronico,edtClave,edtClaveConfirmacion, edtActualizarNombreUsuario;
    ImageButton imgBtnCreateUser;
    Dialog dialog;

    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(SwitchesViewModel.class);
        View root = inflater.inflate(layout.fragment_switches, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        group_id = sharedPreferences.getString("group_id","");

        edtNombreUsuario =root.findViewById(id.edtnombreusuario);
        edtCorreoEletronico=root.findViewById(id.edtcorreoelectronico);
        edtClave=root.findViewById(id.edtclave);
        edtClaveConfirmacion=root.findViewById(id.edtclaveconfirmar);

        imgBtnCreateUser = root.findViewById(id.imgBtnCreateSwitch);

        /*
        listView = root.findViewById(id.listaSwtiches);
        lista = new ArrayList<>();
        adaptador = new AdaptadorSwitches(getContext(), layout.item_switches, lista);

         */

        listView = root.findViewById(id.listaUsers);
        listausuario = new ArrayList<>();
        adaptadorusuario = new AdaptadorUsuario(getContext(), layout.item_usuario, listausuario);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idusername=listausuario.get(position).getId();
                System.out.println("idusername:id"+idusername);
                mostrarDialogCambiarNombre();
             //   Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Para eliminar un switch comunícate con el soporte al 1977 ", Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(adaptadorusuario);
        imgBtnCreateUser.setFocusable(false);
        imgBtnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Para crear un switch comunícate con el soporte al 1977", Toast.LENGTH_LONG).show();
            }
        }) ;

        System.out.println("grupo sw id : "+group_id);
      //  getSwitches(getString(string.ip_and_port) + "edelhome/listSwitch.php?group_id="+ group_id);
      //  downloadJsonUsers(getString(string.ip_and_port) + "edelhome/getGroupUsers.php?group_id="+ group_id);
        downloadJsonUsers(getString(R.string.ip_and_port) + "edelhome/getGroupUsers.php?group_id="+ group_id);

        return root;

    }


    public void mostrarDialogCambiarNombre(/*, final int position*/) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.actualizarnombreusuario);
        dialog.setTitle("Cambiar nombre");

       final EditText edtActualizarNombreUsuario = dialog.findViewById(R.id.edtactualizarnombreusuario);

        Button btncambiarnombreusuario = dialog.findViewById(R.id.btncambiarnombreusuario);
        Button btncancelarcambionombre = dialog.findViewById(R.id.btncancelarcambionombre);

        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 1);

        int height = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.3);
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        dialog.show();

        btncambiarnombreusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         if (TextUtils.isEmpty(edtActualizarNombreUsuario.getText().toString().trim())) {
            edtActualizarNombreUsuario.setError("El campo esta vacio");
         System.out.println("datos vacios");
         } else {
         System.out.println("datos ");
            newUserName = edtActualizarNombreUsuario.getText().toString().trim();
            editUsername();
            Toast.makeText(getContext(), newUserName, Toast.LENGTH_LONG).show();

        }
            }
        });

        btncancelarcambionombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }


        });
    }


    private void getSwitches(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] stocks = new String[jsonArray.length()];
        String sesion="Estado del foco: ";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String userId = obj.getString("switch_id");
            String place = obj.getString("place");
            String bulb_state = obj.getString("bulb_state");
            if(bulb_state == "1" ){
                bulb_state = sesion.concat("Encendido");
            }else {
                bulb_state = sesion.concat("Apagado");
            }
            stocks[i] = obj.getString("place") + " " + obj.getString("bulb_state");
                lista.add(new Switches(Integer.parseInt(userId), String.format("Cuarto de: "+place), bulb_state, group_id));

        }
        adaptador.notifyDataSetChanged();

    }

    private void downloadJsonUsers(final String urlWebService) {

        class DownloadJsonUsers extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadListViewUsers(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJsonUsers getJSON = new DownloadJsonUsers();
        getJSON.execute();
    }

    private void loadListViewUsers(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] stocks = new String[jsonArray.length()];
        String sesion="Cuarto de: ";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String userId = obj.getString("user_id");
            String name = sesion.concat(obj.getString("username"));
           // stocks[i] = obj.getString("username");
          //  if(!userId.contentEquals(userId.trim())){
                listausuario.add(new Usuario(name,Integer.parseInt(userId)));
          //  }

        }
        adaptadorusuario.notifyDataSetChanged();

    }

    private void editUsername() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.246/edelhome/editUsername.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    System.out.println("Vacio"+response.toString());
                    //dialog.dismiss();
                } else {
                   // System.out.println("Respuestar error"+response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR"+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("username",newUserName);
                parametros.put("user_id",String.valueOf(idusername));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}