package com.example.fera1999.edelh.ui.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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


public class UsersFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String ip="192.168.43.246";
    String nuevonombreusuario;
    String nuevocorreousuario;
    String nuevaclaveusuario;
    String claveconfirmacion;
    private UsersViewModel galleryViewModel;
    ListView listView;
    ArrayList<Usuario> lista;
    AdaptadorUsuario adaptador=null;

    EditText edtnombreusuario,edtcorreoeletronico,edtclave,edtclaveconfirmacion;
    EditText edtactualizarnombreusuario;
    Button btnguardar,btncancelar;
    ImageButton imbcrearnuevousuario;
    Spinner sptipousuario,spgrupo;

    String nombreusuarioeliminar="";
    Layout nuevousuario;
    private String idgrupousuario="1";
    private int selecciontipousuario=0;
    private int selecciongrupo=0;
    int eliminaridususario=0;
    String[] tipousuario={"Regular","Administrador"};
    String[] grupo={"Seleccione grupo","1","2","3","4","5"};

    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;
    Dialog dialog;
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

        listView = (ListView) root.findViewById(R.id.listausuario);
        lista=new ArrayList<>();
        adaptador=new AdaptadorUsuario(getContext(),R.layout.itemusuario,lista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            alertDialog();
            eliminaridususario=lista.get(position).getId();
            nombreusuarioeliminar=lista.get(position).getNombre();
            System.out.println(nombreusuarioeliminar+" "+eliminaridususario);
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


        downloadJSON("http://192.168.43.246/edelhome/getGroupUsers.php");
   /*
        final ToggleButton tbeditdelete=root.findViewById(R.id.tbactualizareliminar);

        tbeditdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tbeditdelete.isChecked()){
                    Toast.makeText(getContext(),"eliminar",Toast.LENGTH_LONG).show();
                    deleteUser(eliminaridususario);
                }else{
                    Toast.makeText(getContext(),"actualizar",Toast.LENGTH_LONG).show();
                    mostrarDialogCambiarNombre(getActivity());
                }
            }
        }); */

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
                        System.out.println("DELETE ID "+eliminaridususario);
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

        edtactualizarnombreusuario = dialog.findViewById(R.id.edtactualizarnombreusuario);

        Button btncambiarnombreusuario = dialog.findViewById(R.id.btncambiarnombreusuario);
        Button btncancelarcambionombre = dialog.findViewById(R.id.btncancelarcambionombre);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.5);

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btncambiarnombreusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtactualizarnombreusuario.getText().toString().trim())) {
                    edtactualizarnombreusuario.setError("El campo esta vacio");
                } else {
                    nuevonombreusuario = edtactualizarnombreusuario.getText().toString().trim();
                    Toast.makeText(getContext(), nuevonombreusuario, Toast.LENGTH_LONG).show();

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

        edtnombreusuario=dialog.findViewById(R.id.edtnombreusuario);
        edtcorreoeletronico=dialog.findViewById(R.id.edtcorreoelectronico);
        edtclave=dialog.findViewById(R.id.edtclave);
        edtclaveconfirmacion=dialog.findViewById(R.id.edtclaveconfirmar);

        sptipousuario=dialog.findViewById(R.id.tipousuario);
        spgrupo=dialog.findViewById(R.id.idgrupo);
        btnguardar=dialog.findViewById(R.id.btnguardarnuevousuario);
        btncancelar=dialog.findViewById(R.id.btncancelar);

        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, tipousuario);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptipousuario.setAdapter(aa);

        ArrayAdapter bb = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, grupo);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spgrupo.setAdapter(bb);

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

        spgrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {
                Toast.makeText(getContext(), grupo[position], Toast.LENGTH_SHORT).show();
                selecciongrupo=spgrupo.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1 );

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
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
            int idusuario=obj.getInt("user_id");
            String nombre=obj.getString("username");
            String ultimasesion=sesion.concat(obj.getString("last_login"));
            stocks[i] = obj.getString("username") + " " + obj.getString("last_login");
            System.out.println(stocks[i]);
            lista.add(new Usuario(idusuario,nombre,ultimasesion));
            System.out.println("LISTA "+lista);
        }
        adaptador.notifyDataSetChanged();

    }

    private void cargarWebService() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.246/edelhome/createUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    System.out.println("Vacio"+response);
                } else {
                    System.out.println("Respuestar error"+response);
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
                parametros.put("username",nuevonombreusuario);
                parametros.put("email",nuevocorreousuario);
                parametros.put("pass",nuevaclaveusuario);
                parametros.put("administrador",String.valueOf(selecciontipousuario));
                parametros.put("group_id",String.valueOf(selecciongrupo));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);



    }

    private void deleteUser(final int idUsuario) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.246/edelhome/deleteUser.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    System.out.println("Vacio"+response.toString());
                    dialog.dismiss();
                } else {
                    System.out.println("Respuestar error"+response.toString());
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
                parametros.put("user_id",String.valueOf(idUsuario));
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