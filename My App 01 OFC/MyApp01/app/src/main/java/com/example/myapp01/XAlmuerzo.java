package com.example.myapp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class XAlmuerzo extends AppCompatActivity {
    private ImageButton Ibt1,Ibt2,Ibt3;
    ImageButton btnimenu,btnuserd;
    private ImageButton Cam;
    private ImageButton usuariom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcomida);

        Ibt1= (ImageButton) findViewById(R.id.btnensalada1);
        Ibt2= (ImageButton) findViewById(R.id.btnensalada2);
        Ibt3= (ImageButton) findViewById(R.id.btnensalada3);

        btnimenu= (ImageButton) findViewById(R.id.imageButton);
        usuariom= (ImageButton) findViewById(R.id.btnusuario);

        //
        Cam= (ImageButton) findViewById(R.id.camara);
        Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XAlmuerzo.this, Reconocimiento_objetos.class));
            }
        });

        usuariom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(XAlmuerzo.this,Userd.class));

            }
        });

        Ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XAlmuerzo.this,XAlmuerzo_1.class));
            }
        });
        Ibt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XAlmuerzo.this,XCena_1.class));
            }
        });
        Ibt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XAlmuerzo.this,XDesayuno_1.class));
            }
        });


        btnimenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XAlmuerzo.this,Menu.class));
            }
        });


    }
}