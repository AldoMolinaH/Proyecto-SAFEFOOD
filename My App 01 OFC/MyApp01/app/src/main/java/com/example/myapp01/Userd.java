package com.example.myapp01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userd extends AppCompatActivity {

    Button btnsalir,btnmodificar;
    ImageButton btnimenu,btnuserd;
    private ImageButton Cam;
    private TextView xtnombre;
    private TextView xtedad;
    private TextView xtsexo;
    private TextView xtestatura;
    private TextView xtpeso;

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_usuario);

        btnsalir=(Button)findViewById(R.id.btnsalir);
        btnmodificar=(Button)findViewById(R.id.btnmodificar);
        btnimenu= (ImageButton) findViewById(R.id.imageButton);

        mAuth=FirebaseAuth.getInstance();
        mDataBase= FirebaseDatabase.getInstance().getReference();


        xtnombre=(TextView)findViewById(R.id.txnombre);
        xtedad=(TextView)findViewById(R.id.txedad);
        xtsexo=(TextView)findViewById(R.id.txsexo);
        xtestatura=(TextView)findViewById(R.id.txestatura);
        xtpeso=(TextView)findViewById(R.id.txpeso);

        // private ImageButton Cam;
        Cam= (ImageButton) findViewById(R.id.camara);
        Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Userd.this, Reconocimiento_objetos.class));
            }
        });

       btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Userd.this, Login.class));
                finish();
            }
        });

       getUsersInfo();//llamado al metodo para mostrar informacion del usuario

        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Userd.this, UserdModificar.class));
            }
        });

        btnimenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Userd.this,Menu.class));
            }
        });
    }

    private  void getUsersInfo(){
        String id = mAuth.getCurrentUser().getUid();

        mDataBase.child("users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String name     =dataSnapshot.child("nombre").getValue().toString();
                    String edadd    =dataSnapshot.child("edad").getValue().toString();
                    String sex      =dataSnapshot.child("sexo").getValue().toString();
                    String talla    =dataSnapshot.child("talla").getValue().toString();
                    String pes      =dataSnapshot.child("peso").getValue().toString();

                    xtnombre.setText(name);
                    xtedad.setText(edadd);
                    xtsexo.setText(sex);
                    xtestatura.setText(talla);
                    xtpeso.setText(pes);


                }else{

                    Toast.makeText(Userd.this, "Datos en carga...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
