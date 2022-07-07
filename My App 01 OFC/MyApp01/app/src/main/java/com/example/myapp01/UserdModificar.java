package com.example.myapp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserdModificar extends AppCompatActivity {
    Button Cancelar;
    Button botonReg,botonpasReg;

    TextInputEditText mail, pass,pass2, name , age , genus, weight;
    Button btnGuardar;
    TextInputLayout layTall;
    AutoCompleteTextView autoTall;
    int n_mayus= 0;
    int n_numeros= 0;
    int n_espacios= 0;
    int n_especiales = 0;
    int error=0;
    int validacion=0;
    String messageErrorMail = "";
    String messageErrorPass = "";
    String messageErrorName = "";

    private String auxMail="";
    private String auxPass="";
    private String auxPass2="";
    private String auxName="";
    private String auxAno="";
    private String auxGenus="";
    private String auxHe="";
    private String auxWei="";


    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userd_modificar);

        Cancelar = (Button) findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnAceptar);
        mail = findViewById(R.id.textfieldCorreo);
        pass = findViewById(R.id.textfieldPassword1);
        pass2 = findViewById(R.id.textfieldPassword2);
        name = findViewById(R.id.textfieldName);
        age = findViewById(R.id.textfieldAge);
        genus = findViewById(R.id.textfieldGenero);
        autoTall = findViewById(R.id.autoCompleteTall);
        weight = findViewById(R.id.textfieldPeso);

        botonReg = findViewById(R.id.btnAceptar);
        botonpasReg = (Button) findViewById(R.id.btnAceptar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //============METODOS=================================

        String[] alturas = new String[]{
                "2.20 cm",
                "2.18 cm",
                "2.16 cm",
                "2.14 cm",
                "2.12 cm",
                "2.10 cm",
                "2.08 cm",
                "2.06 cm",
                "2.02 cm",
                "2.00 cm",
                "1.98 cm",
                "1.96 cm",
                "1.94 cm",
                "1.92 cm",
                "1.90 cm",
                "1.88 cm",
                "1.86 cm",
                "1.84 cm",
                "1.82 cm",
                "1.80 cm",
                "1.78 cm",
                "1.76 cm",
                "1.74 cm",
                "1.72 cm",
                "1.70 cm",
                "1.68 cm",
                "1.66 cm",
                "1.64 cm",
                "1.62 cm",
                "1.60 cm",
                "1.58 cm",
                "1.56 cm",
                "1.54 cm",
                "1.52 cm",
                "1.50 cm",
                "1.48 cm",
                "1.46 cm",
                "1.44 cm",
                "1.42 cm",
                "1.40 cm",
                "1.38 cm",
                "1.36 cm",
                "1.34 cm",
                "1.32 cm",
                "1.30 cm",
                "1.28 cm",
                "1.26 cm",
                "1.24 cm",
                "1.22 cm",
                "1.20 cm",

        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                UserdModificar.this,
                R.layout.dropdown_item,
                alturas
        );

        autoTall.setAdapter(adapter);

        //============METODOS=================================
        CargarDatos();//muestra los datos en los recuadros
        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserdModificar.this, Userd.class));
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion = 0;

                // VALIDACION EMAIL
                messageErrorMail = "";
                auxMail = mail.getText().toString();
                if (auxMail.length() != 0 ){
                    String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(auxMail);
                    if (!matcher.matches()) {
                        messageErrorMail += "Ingrese un correo valido";
                        mail.setError(messageErrorMail);
                    } else {
                        validacion++;
                    }
                }else{
                    mail.setError("INGRESE CORREO ELECTRONICO");
                }



                // VALIDACION PASSWORD
                n_mayus = 0;
                n_numeros = 0;
                n_espacios = 0;
                n_especiales = 0;
                error = 0;
                messageErrorPass = "";
                auxPass = pass.getText().toString();
                String auxPass2 = pass2.getText().toString();
                if (auxPass.length() > 0 ){
                    if (auxPass.length() >= 8) {
                        for (int i = 0; i < auxPass.length(); i++) {
                            if (Character.isUpperCase(auxPass.charAt(i))) {
                                n_mayus++;
                            }
                            if (Character.isDigit(auxPass.charAt(i))) {
                                n_numeros++;
                            }
                            if (Character.isSpaceChar(auxPass.charAt(i))) {
                                n_espacios++;
                            }
                            char auxChar = auxPass.charAt(i);
                            if ((int) auxChar > 32 && (int) auxChar <= 47
                                    || (int) auxChar >= 58 && (int) auxChar <= 64
                                    || (int) auxChar >= 91 && (int) auxChar <= 96
                                    || (int) auxChar >= 123 && (int) auxChar <= 255) {
                                n_especiales++;
                            }
                        }
                        System.out.println("Mayus : " + n_mayus);
                        System.out.println("Numeros : " + n_numeros);
                        System.out.println("Espacios : " + n_espacios);
                        System.out.println("Especiales : " + n_especiales);
                        if (n_mayus < 1) {
                            messageErrorPass += " Por lo menos una mayuscula , ";
                            error++;
                        }
                        if (n_numeros < 1) {
                            messageErrorPass += " Por lo menos un numero , ";
                            error++;
                        }
                        if (n_espacios > 0) {
                            messageErrorPass += " Sin espacios , ";
                            error++;
                        }
                        if (n_especiales < 1) {
                            messageErrorPass += " Por lo menos un caracter especial , ";
                            error++;
                        }

                        if (error > 0) {
                            pass.setError(messageErrorPass);
                        }

                    } else {
                        pass.setError("MINIMO 8 CARACTERES");
                    }
                    if (error == 0) {
                        if (auxPass.equals(auxPass2)) {
                            System.out.println("ENTRA" + auxPass + " == " + auxPass2);
                            validacion++;
                        } else {
                            System.out.println(auxPass + " == " + auxPass2);
                            pass2.setError("NO COINCIDE LA CONTRASEÑA");
                        }
                    }
                }else{
                    pass.setError("INGRESE CONTRASEÑA");
                    if(auxPass2.length() == 0){
                        pass2.setError("INGRESE CONTRASEÑA");
                    }
                }


                // VALIDACION NOMBRE

                n_mayus = 0;
                n_numeros = 0;
                n_espacios = 0;
                n_especiales = 0;
                error = 0;
                messageErrorName = "";
                auxName = name.getText().toString();
                if (auxName.length() > 0 ){
                    if (auxName.length() >= 4 && auxName.length() <= 15) {
                        for (int i = 0; i < auxName.length(); i++) {
                            if (Character.isUpperCase(auxName.charAt(i))) {
                                n_mayus++;
                            }
                            if (Character.isDigit(auxName.charAt(i))) {
                                n_numeros++;
                            }
                            if (Character.isSpaceChar(auxName.charAt(i))) {
                                n_espacios++;
                            }
                            char auxChar = auxName.charAt(i);
                            if ((int) auxChar > 32 && (int) auxChar <= 47
                                    || (int) auxChar >= 58 && (int) auxChar <= 64
                                    || (int) auxChar >= 91 && (int) auxChar <= 96
                                    || (int) auxChar >= 123 && (int) auxChar <= 255) {
                                n_especiales++;
                            }
                        }
                        System.out.println("Mayus : " + n_mayus);
                        System.out.println("Numeros : " + n_numeros);
                        System.out.println("Espacios : " + n_espacios);
                        System.out.println("Especiales : " + n_especiales);
                        if (n_mayus > 1) {
                            messageErrorName += "Solo una mayuscula , ";
                            error++;
                        }
                        if (n_numeros > 0) {
                            messageErrorName += " No numeros , ";
                            error++;
                        }
                        if (n_espacios > 0) {
                            messageErrorName += " No espacios , ";
                            error++;
                        }
                        if (n_especiales > 0) {
                            messageErrorName += " No caracteres especial , ";
                            error++;
                        }

                        if (error > 0) {
                            name.setError(messageErrorName);
                        }

                    } else {
                        if (auxName.length() < 8) {
                            name.setError("MINIMO 4 CARACTERES");
                        } else {
                            name.setError("MAXIMO 15 CARACTERES");
                        }
                    }
                    if (error == 0) {
                        System.out.println(" VALIDADO NOMBRE ");
                        validacion++;
                    }
                }else{
                    name.setError("INGRESE NOMBRE");
                }


                // VALIDACION EDAD
                auxAno = age.getText().toString();
                if(auxAno.length() != 0){
                    int auxAge = Integer.parseInt(auxAno);
                    if (auxAge > 0 && auxAge < 120) {
                        System.out.println(" VALIDADO EDAD ");
                        validacion++;
                    } else {
                        age.setError("Edad No valida");
                    }
                }
                else{
                    age.setError("INGRESE EDAD");
                }


                // VALIDACION SEXO--------------------------------------------------
                auxGenus = genus.getText().toString();
                int error = 0;
                if(auxGenus.length() > 0){
                    if (auxGenus.length() == 1) {
                        if (auxGenus.equals("M") || auxGenus.equals("m") || auxGenus.equals("F") || auxGenus.equals("f")) {
                            System.out.println(" SEXO VALIDO ");
                            validacion++;
                        }
                        else{
                            genus.setError("CARACTER INVALIDO");
                        }

                    } else {
                        genus.setError("MAXIMO 1 CARACTER");
                        error++;
                    }

                }else{
                    genus.setError("INGRESE SEXO");
                }

                // VALIDACION ESTATURA--------------------------------------------------
                error=0;
                auxHe = autoTall.getText().toString();
                if(auxHe.length()!= 0){
                    if (contains(alturas, auxHe)){
                        System.out.println(" VALIDADO ALTURA -> "+auxHe);

                        validacion++;
                    }
                    else{
                        autoTall.setText("");
                        autoTall.setError("INGRESE ALTURA VALIDA");
                    }

                }else{
                    autoTall.setError(" INGRESE ALTURA");
                }


                // VALIDACION PESO--------------------------------------------------
                error=0;
                auxWei = weight.getText().toString();
                if(auxWei.length() != 0){
                    int auxWeight = Integer.parseInt(auxWei);
                    if (auxWeight > 30 && auxWeight < 150) {
                        System.out.println(" VALIDADO PESO ");
                        validacion++;
                    } else {
                        weight.setError("Peso No valido");
                    }
                }
                else{
                    weight.setError("INGRESE PESO");
                }

                if (validacion == 7){
                    ActualizarUser();
                }
            }
        });
    }

    private void CargarDatos() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String correo = dataSnapshot.child("correo").getValue().toString();
                    String pass11 = dataSnapshot.child("pass").getValue().toString();
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String edadd = dataSnapshot.child("edad").getValue().toString();
                    String sex = dataSnapshot.child("sexo").getValue().toString();
                    String talla = dataSnapshot.child("talla").getValue().toString();
                    String pes = dataSnapshot.child("peso").getValue().toString();

                    mail.setText(correo);
                    pass.setText(pass11);
                    pass2.setText(pass11);
                    name.setText(nombre);
                    age.setText(edadd);
                    genus.setText(sex);
                    autoTall.setText(talla);
                    weight.setText(pes);
                } else {

                    Toast.makeText(UserdModificar.this, "Datos en carga...", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ActualizarUser() {
        mDatabase.child("userd").child(mAuth.getUid()).removeValue();
        mAuth.createUserWithEmailAndPassword(auxMail, auxPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("correo",auxMail);
                    map.put("pass",auxPass);
                    map.put("nombre",auxName);
                    map.put("edad",auxAno);
                    map.put("sexo",auxGenus);
                    map.put("talla",auxHe);
                    map.put("peso",auxWei);


                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("users").child(id).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                Toast.makeText(UserdModificar.this, "ModificaciÓn CORRECTA", Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                Toast.makeText(UserdModificar.this, "No se pudo crear los datos en DB", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
    }
    private boolean contains(final String[] alturas,final String i) {
        return Arrays.asList(alturas).contains(i);
    }
}




