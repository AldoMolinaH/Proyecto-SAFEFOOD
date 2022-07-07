package com.example.myapp01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button Registro,IngresoU,Encuesta;

    private EditText txtvcorreo;
    private EditText txtvpass;
    private String correo="";
    private String pass="";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_login);

        txtvcorreo = (EditText) findViewById(R.id.txtcorreo);
        txtvpass = (EditText) findViewById(R.id.txtpass);
        IngresoU = (Button) findViewById(R.id.Login);

        mAuth = FirebaseAuth.getInstance();

        //========================================================================================

        Encuesta = (Button) findViewById(R.id.encuesta);
        Encuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Encuesta.class));
            }
        });
        //========================================================================================
        //========================================================================================

        Registro = (Button) findViewById(R.id.Registrarse);
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainRegistro.class);
                startActivity(i);
            }
        });
        //========================================================================================

        IngresoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correo = txtvcorreo.getText().toString();
                pass = txtvpass.getText().toString();
                if (!correo.isEmpty() && !pass.isEmpty()) {
                    loginuser();
                }else{
                    Toast.makeText(Login.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private void loginuser(){
        mAuth.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    onStart();
                    //startActivity(new Intent(Login.this, Menu.class));
                    finish();
                }else{
                    Toast.makeText(Login.this, "Usuario o password INCORRECTO", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(Login.this, Menu.class));
            finish();
        }
    }

}