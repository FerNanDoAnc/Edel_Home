package com.example.fera1999.edelh.ui.switches;

import android.app.Activity;
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

import com.example.fera1999.edelh.R;
import com.example.fera1999.edelh.adaptadores.AdaptadorSwitches;
import com.example.fera1999.edelh.clases.Switches;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;


public class SwitchesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    String newUserName, group_id;
    private SwitchesViewModel galleryViewModel;
    ListView listView;
    ArrayList<Switches> lista;
    AdaptadorSwitches adaptador = null;

    EditText edtNombreUsuario, edtCorreoEletronico,edtClave,edtClaveConfirmacion, edtActualizarNombreUsuario;
    ImageButton imgBtnCreateUser;

    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(SwitchesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_switches, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        group_id = sharedPreferences.getString("group_id","");

        edtNombreUsuario =root.findViewById(R.id.edtnombreusuario);
        edtCorreoEletronico=root.findViewById(R.id.edtcorreoelectronico);
        edtClave=root.findViewById(R.id.edtclave);
        edtClaveConfirmacion=root.findViewById(R.id.edtclaveconfirmar);

        imgBtnCreateUser = root.findViewById(R.id.imgBtnCreateSwitch);


        listView = root.findViewById(R.id.listaSwtiches);
        lista = new ArrayList<>();
        adaptador = new AdaptadorSwitches(getContext(),R.layout.item_switches, lista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDialogCambiarNombre(getActivity());
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Para eliminar un switch comunícate con el soporte al 1977 ", Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(adaptador);

        imgBtnCreateUser.setFocusable(false);
        imgBtnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Para crear un switch comunícate con el soporte al 1977", Toast.LENGTH_LONG).show();
            }
        }) ;

        getSwitches(getString(R.string.ip_and_port) + "edelhome/listSwitch.php?group_id="+ group_id);

        return root;

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
                lista.add(new Switches(Integer.parseInt(userId), place, bulb_state, group_id));

        }
        adaptador.notifyDataSetChanged();

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