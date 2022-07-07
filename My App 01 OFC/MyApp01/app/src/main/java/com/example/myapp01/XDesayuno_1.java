package com.example.myapp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class XDesayuno_1 extends AppCompatActivity {
    //private ImageButton Ibt1;
    ImageButton btnimenu,btnuserd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcomida_3);

        btnimenu= (ImageButton) findViewById(R.id.imageButton);


        btnimenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XDesayuno_1.this,XAlmuerzo.class));
            }
        });

       /* Ibt1= (ImageButton) findViewById(R.id.ibtn1);


        Ibt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(XDesayuno.this,XDesayuno_1.class));

            }
        });*/


    }
}