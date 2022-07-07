package com.example.myapp01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Bienvenida extends AppCompatActivity {
    Button siguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        Intent intentBackgroundService = new Intent(this,
                MyService.class);
        startService(intentBackgroundService);


        siguiente=(Button)findViewById(R.id.Continuar);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent( Bienvenida.this,Login.class);
                startActivity(i);
            }
        });
    }
}