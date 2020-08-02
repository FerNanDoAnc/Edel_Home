package com.example.fera1999.edelh.ui.switches;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.adaptadores.AdaptadorUsuario;
import com.example.fera1999.edelh.adaptadores.AdaptadorUsuarioHabitacion;
import com.example.fera1999.edelh.clases.Usuario;
import com.example.fera1999.edelh.clases.UsuarioHabitacion;

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

public class SwitchesFragment extends Fragment {

    private SwitchesViewModel slideshowViewModel;
    ListView listView;
    ArrayList<UsuarioHabitacion> lista;
    AdaptadorUsuarioHabitacion adaptador=null;
    StringRequest stringRequest;
    Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SwitchesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_switches, container, false);
        listView = (ListView) root.findViewById(R.id.listausuario);
        lista=new ArrayList<>();
        adaptador=new AdaptadorUsuarioHabitacion(getContext(),R.layout.itemhabitacion,lista);
        downloadJSON("http://192.168.43.246/edelhome/getGroupUsers.php");
        return root;
    }


    private void downloadJSON(final String urlWebService) {

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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            int idusuario=obj.getInt("user_id");
            String nombre=obj.getString("username");

            stocks[i] = obj.getString("username") + " " + obj.getString("last_login");
            System.out.println(stocks[i]);
            lista.add(new UsuarioHabitacion(idusuario,idusuario,nombre));
            System.out.println("LISTA "+lista);
        }
        adaptador.notifyDataSetChanged();

    }



}