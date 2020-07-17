package com.example.fera1999.edelh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecuperarClaveActivity extends AppCompatActivity {


    EditText claveactual,nuevaclave,confirmarclave;
    Button btnguardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);

        claveactual=findViewById(R.id.edtclaveactual);
        nuevaclave=findViewById(R.id.edtnuevaclave);
        confirmarclave=findViewById(R.id.edtconfirmarclave);
        btnguardar=findViewById(R.id.btnguardar);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (claveactual.getText().toString().trim().equals("1234")){

                if (confirmarclave.getText().toString().trim().equals(nuevaclave.getText().toString().trim())){

                  Toast.makeText(RecuperarClaveActivity.this,"datos guardados correcatamente", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(RecuperarClaveActivity.this, LoginActivity.class);
                  startActivity(intent);

                }else{

                  Toast.makeText(RecuperarClaveActivity.this,"datos no coinciden", Toast.LENGTH_SHORT).show();
                }
                }else{
                  Toast.makeText(RecuperarClaveActivity.this,"datos no coinciden", Toast.LENGTH_SHORT).show();
              }
            claveactual.setText("");
            confirmarclave.setText("");
            nuevaclave.setText("");


            }
        });

    }
}
