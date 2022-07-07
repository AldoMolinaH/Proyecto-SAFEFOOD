package com.example.myapp01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {
    private ImageButton usuariom,Cam,Encuesta;
    private Button btnAlmuerzo,btnBebida;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_menu);

        usuariom= (ImageButton) findViewById(R.id.btnusuario);


        btnAlmuerzo= (Button) findViewById(R.id.btnalmuerzo);
        btnBebida= (Button) findViewById(R.id.btnbebida);

        usuariom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this,Userd.class));
            }
        });
       // private ImageButton Cam;
        Cam= (ImageButton) findViewById(R.id.camara);
        Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, Reconocimiento_objetos.class));
            }
        });

        btnAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, XAlmuerzo.class));
            }
        });

        btnBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu.this, XBebida.class));
            }
        });


    }
}