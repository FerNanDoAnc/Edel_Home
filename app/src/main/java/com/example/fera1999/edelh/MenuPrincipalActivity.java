package com.example.fera1999.edelh;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    TextView user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        user=findViewById(R.id.usuariomenu);
        Bundle extras = getIntent().getExtras();
        String usuario = "";
        if(extras != null){
            usuario = extras.getString("username");
            user.setText("Bienvenido " + usuario);
        }
    }
}
