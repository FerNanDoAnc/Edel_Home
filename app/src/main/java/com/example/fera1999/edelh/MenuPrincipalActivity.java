package com.example.fera1999.edelh;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

public class MenuPrincipalActivity extends AppCompatActivity {

    TextView user;

    DrawerLayout drawerlayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerlayout= findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);

        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerlayout, toolbar,R.string.open,R.string.close);
        drawerlayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        user=findViewById(R.id.usuario);
        Bundle extras = getIntent().getExtras();
        String usuario = "";
        if(extras != null){
            usuario = extras.getString("username");
            user.setText("Bienvenido " + usuario);
        }
    }
}
