package com.example.fera1999.edelh.ui.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.fera1999.edelh.R;
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


public class UsersFragment extends Fragment {
    String ip="192.168.43.246";
    String nuevonombreusuario;
    String nuevocorreousuario;
    String nuevaclaveusuario;
    String claveconfirmacion;
    private UsersViewModel galleryViewModel;
    ListView listView;

    EditText edtnombreusuario,edtcorreoeletronico,edtclave,edtclaveconfirmacion;
    Button btnguardar,btncancelar;
    ImageButton imbcrearnuevousuario;
    private String idgrupousuario="1";

    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;
    ProgressDialog progreso;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(UsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);

        edtnombreusuario=root.findViewById(R.id.edtnombreusuario);
        edtcorreoeletronico=root.findViewById(R.id.edtcorreoelectronico);
        edtclave=root.findViewById(R.id.edtclave);
        edtclaveconfirmacion=root.findViewById(R.id.edtclaveconfirmar);

        imbcrearnuevousuario=root.findViewById(R.id.imbcrearnuevousuario);

        btnguardar=root.findViewById(R.id.btnguardarnuevousuario);

        imbcrearnuevousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mostrarDialogCrearUsuario(getActivity());
            }
        });


            listView = (ListView) root.findViewById(R.id.listausuario);
            downloadJSON("http://192.168.43.246/getGroupUsers.php");

        return root;

    }

    private void mostrarDialogCrearUsuario(Activity activity /*, final int position*/){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.agreganuevorusuario);
        dialog.setTitle("Ingrese datos del nuevo usuario");

        edtnombreusuario=dialog.findViewById(R.id.edtnombreusuario);
        edtcorreoeletronico=dialog.findViewById(R.id.edtcorreoelectronico);
        edtclave=dialog.findViewById(R.id.edtclave);
        edtclaveconfirmacion=dialog.findViewById(R.id.edtclaveconfirmar);

        btnguardar=dialog.findViewById(R.id.btnguardarnuevousuario);
        btncancelar=dialog.findViewById(R.id.btncancelar);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels *1.2 );

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtnombreusuario.getText().toString().trim())){
                    edtnombreusuario.setError("El nombre usuario ya existe");
                }else if (TextUtils.isEmpty(edtcorreoeletronico.getText().toString().trim())){
                    edtcorreoeletronico.setError("El correo ya existe");
                }else if (TextUtils.isEmpty(edtclave.getText().toString().trim())){
                    edtclave.setError("Las contraseñas no coinciden");
                }else if (TextUtils.isEmpty(edtclaveconfirmacion.getText().toString().trim())){
                    edtclaveconfirmacion.setError("Las contraseñas no coinciden");
                }else{
                     nuevonombreusuario=edtnombreusuario.getText().toString().trim();
                     nuevocorreousuario=edtcorreoeletronico.getText().toString().trim();
                     nuevaclaveusuario=edtclave.getText().toString().trim();
                     claveconfirmacion=edtclaveconfirmacion.getText().toString().trim();
                     Toast.makeText(getContext(),nuevonombreusuario,Toast.LENGTH_LONG).show();
                    cargarWebService();

                }

            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


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
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
            ArrayList<Usuario> usuariolista = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Usuario ul=new Usuario();
            ul.setNombre(obj.getString("username"));
            ul.setLastlogin("last_login");
            stocks[i] = obj.getString("username") + " " + obj.getString("last_login");
            System.out.println(stocks[i]);
            usuariolista.add(ul);
            System.out.println("LISTA "+usuariolista);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_multiple_choice,stocks);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.itemusuario,stocks);

        listView.setAdapter(arrayAdapter);

    }

    private void cargarWebService() {

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();



        String url=ip+"/createUser.php?";

        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progreso.hide();

                if (response.trim().equalsIgnoreCase("registra")){
                    edtnombreusuario.setText("");
                    edtcorreoeletronico.setText("");
                    edtclave.setText("");
                    Toast.makeText(getContext(),"Se ha registrado con exito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"No se ha registrado ",Toast.LENGTH_SHORT).show();
                    Log.i("RESPUESTA: ",""+response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se ha podido conectar",Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
              /*
                String documento=campoDocumento.getText().toString();
                String nombre=campoNombre.getText().toString();
                String profesion=campoProfesion.getText().toString();

               */

                Map<String,String> parametros=new HashMap<>();
                parametros.put("usuario",nuevonombreusuario);
                parametros.put("correo",nuevocorreousuario);
                parametros.put("clave",nuevaclaveusuario);

                return parametros;
            }
        };

        /*
        //request.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);

         */
    }

}