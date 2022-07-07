package com.example.myapp01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_screem extends AppCompatActivity {
    ProgressBar pb;
    int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Intent intentBackgroundService = new Intent(this,
                MyService.class);
        startService(intentBackgroundService);

        //========================================================================================
        pb=(ProgressBar) findViewById(R.id.pb);

        final Timer t=new Timer();
        TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);
                if(counter==10){
                    if(counter==10){
                        Intent i= new Intent( Splash_screem.this,Login.class);
                        startActivity(i);
                    }
                    t.cancel();


                }

            }
        };
        t.schedule(tt,5,500);



        //========================================================================================

    }
}