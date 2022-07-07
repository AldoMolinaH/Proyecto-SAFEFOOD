package com.example.myapp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class XBebida extends AppCompatActivity {
    private ImageButton Ibt1;
    private ImageButton Cam;
    ImageButton btnimenu,btnuserd;
    private ImageButton usuariom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xbebida);

        Ibt1= (ImageButton) findViewById(R.id.btnensalada1);


        btnimenu= (ImageButton) findViewById(R.id.imageButton);
        usuariom= (ImageButton) findViewById(R.id.btnusuario);

        Cam= (ImageButton) findViewById(R.id.camara);
        Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XBebida.this, Reconocimiento_objetos.class));
            }
        });
        usuariom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(XBebida.this,Userd.class));

            }
        });

        btnimenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XBebida.this,Menu.class));
            }
        });
        Ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(XBebida.this,XBebida_1.class));

            }
        });


    }
}