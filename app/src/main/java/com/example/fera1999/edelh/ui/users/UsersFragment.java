package com.example.fera1999.edelh.ui.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.adaptadores.AdaptadorUsuario;
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


public class UsersFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String newUserName, newEmail, newPassword, newPasswordConfirm, group_id, user_id;
    private UsersViewModel galleryViewModel;
    ListView listView;
    ArrayList<Usuario> lista;
    AdaptadorUsuario adaptador = null;

    EditText edtNombreUsuario, edtCorreoEletronico,edtClave,edtClaveConfirmacion, edtActualizarNombreUsuario;
    Button btnguardar,btncancelar;
    ImageButton imbcrearnuevousuario;
    Spinner sptipousuario;

    String nombreusuarioeliminar = "";
    private int selecciontipousuario = 0;
    int eliminaridususario = 0;
    String[] tipousuario={"Regular","Administrador"};

    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(UsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        group_id = sharedPreferences.getString("group_id","");
        user_id = sharedPreferences.getString("user_id","");

        edtNombreUsuario =root.findViewById(R.id.edtnombreusuario);
        edtCorreoEletronico=root.findViewById(R.id.edtcorreoelectronico);
        edtClave=root.findViewById(R.id.edtclave);
        edtClaveConfirmacion=root.findViewById(R.id.edtclaveconfirmar);

        imbcrearnuevousuario=root.findViewById(R.id.imbcrearnuevousuario);

        btnguardar=root.findViewById(R.id.btnguardarnuevousuario);

        listView = (ListView) root.findViewById(R.id.listausuario);
        lista = new ArrayList<>();
        adaptador = new AdaptadorUsuario(getContext(),R.layout.itemusuario,lista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            alertDialog();
            eliminaridususario=lista.get(position).getId();
            nombreusuarioeliminar=lista.get(position).getNombre();
         }
        });

        listView.setAdapter(adaptador);

        imbcrearnuevousuario.setFocusable(false);
        imbcrearnuevousuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mostrarDialogCrearUsuario(getActivity());
            }
        }) ;


        downloadJSON(getString(R.string.ip_and_port) + "edelhome/getGroupUsers.php?group_id="+ group_id);

        return root;

    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getContext());
        dialog.setMessage("Eliminar Usuario");
        dialog.setTitle("¿Estás seguro que deseas eliminar al usuario "+nombreusuarioeliminar+" ?");
        dialog.setPositiveButton("Eliminar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        deleteUser(eliminaridususario);
                        Toast.makeText(getContext(),"Se realizó la acción",Toast.LENGTH_LONG).show();
                    }
                });

        dialog.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Se canceló la acción",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void mostrarDialogCambiarNombre(Activity activity /*, final int position*/) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.actualizarnombreusuario);
        dialog.setTitle("Cambiar nombre");

        edtActualizarNombreUsuario = dialog.findViewById(R.id.edtactualizarnombreusuario);

        Button btncambiarnombreusuario = dialog.findViewById(R.id.btncambiarnombreusuario);
        Button btncancelarcambionombre = dialog.findViewById(R.id.btncancelarcambionombre);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.5);

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        dialog.show();

        btncambiarnombreusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtActualizarNombreUsuario.getText().toString().trim())) {
                    edtActualizarNombreUsuario.setError("El campo esta vacio");
                } else {
                    newUserName = edtActualizarNombreUsuario.getText().toString().trim();
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

    private void mostrarDialogCrearUsuario(Activity activity /*, final int position*/){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.agreganuevorusuario);
        dialog.setTitle("Ingrese datos del nuevo usuario");

        edtNombreUsuario =dialog.findViewById(R.id.edtnombreusuario);
        edtCorreoEletronico=dialog.findViewById(R.id.edtcorreoelectronico);
        edtClave=dialog.findViewById(R.id.edtclave);
        edtClaveConfirmacion=dialog.findViewById(R.id.edtclaveconfirmar);

        sptipousuario=dialog.findViewById(R.id.tipousuario);
        btnguardar=dialog.findViewById(R.id.btnguardarnuevousuario);
        btncancelar=dialog.findViewById(R.id.btncancelar);

        ArrayAdapter aa = new ArrayAdapter(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_item, tipousuario);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptipousuario.setAdapter(aa);

        sptipousuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {
                Toast.makeText(getContext(), tipousuario[position], Toast.LENGTH_SHORT).show();
                selecciontipousuario=sptipousuario.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        dialog.show();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNombreUsuario.getText().toString().trim())){
                    edtNombreUsuario.setError("El nombre usuario ya existe");
                }else if (TextUtils.isEmpty(edtCorreoEletronico.getText().toString().trim())){
                    edtCorreoEletronico.setError("El correo ya existe");
                }else if (TextUtils.isEmpty(edtClave.getText().toString().trim())){
                    edtClave.setError("Las contraseñas no coinciden");
                }else if (TextUtils.isEmpty(edtClaveConfirmacion.getText().toString().trim())){
                    edtClaveConfirmacion.setError("Las contraseñas no coinciden");
                }else{
                     newUserName = edtNombreUsuario.getText().toString().trim();
                     newEmail =edtCorreoEletronico.getText().toString().trim();
                     newPassword =edtClave.getText().toString().trim();
                     newPasswordConfirm =edtClaveConfirmacion.getText().toString().trim();
                     Toast.makeText(getContext(),"Creando Usuario...",Toast.LENGTH_LONG).show();
                     cargarWebService();
                     dialog.dismiss();

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
            String sesion="Último inicio de sesión: ";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String userId = obj.getString("user_id");
            String name = obj.getString("username");
            String lastSession = sesion.concat(obj.getString("last_login"));
            stocks[i] = obj.getString("username") + " " + obj.getString("last_login");
            if(!user_id.contentEquals(userId.trim())){
                lista.add(new Usuario(Integer.parseInt(userId), name, lastSession));
            }

        }
        adaptador.notifyDataSetChanged();

    }

    private void cargarWebService() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip_and_port) + "edelhome/createUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Usuario creado con éxito", Toast.LENGTH_LONG).show();
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();

                ft.detach(UsersFragment.this).attach(UsersFragment.this).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("username", newUserName);
                parametros.put("email", newEmail);
                parametros.put("pass", newPassword);
                parametros.put("administrador",String.valueOf(selecciontipousuario));
                parametros.put("group_id", group_id);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueue.add(stringRequest);
    }

    private void deleteUser(final int idUsuario) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.ip_and_port) +"edelhome/deleteUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Usuario eliminado con éxito", Toast.LENGTH_LONG).show();
                    FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();

                    ft.detach(UsersFragment.this).attach(UsersFragment.this).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("user_id",String.valueOf(idUsuario));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
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