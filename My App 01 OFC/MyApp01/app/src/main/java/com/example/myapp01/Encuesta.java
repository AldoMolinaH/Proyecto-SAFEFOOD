package com.example.myapp01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Encuesta extends AppCompatActivity{
    private Button btnlink;
    private ImageButton btnReturn1;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encuesta);

        btnlink = (Button) findViewById(R.id.btnlink);
        url="https://forms.gle/1rihxuGNAQxiNGbe7";

        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(url);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnReturn1= (ImageButton) findViewById(R.id.retorno);
        btnReturn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Encuesta.this,Login.class));
            }
        });





    }
}


