package com.example.fera1999.edelh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText clave, usuario;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario=findViewById(R.id.usuario);
        clave=findViewById(R.id.clave);
    }
    public void Login(View view) {
       if((usuario.getText().toString().equalsIgnoreCase("usuario") )&&(clave.getText().toString().equals("1234"))){
        Intent intent = new Intent(LoginActivity.this, MenuPrincipalActivity.class);
        intent.putExtra("username", usuario.getText().toString());
        startActivity(intent);
    }else{
        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
    }
    }
}
