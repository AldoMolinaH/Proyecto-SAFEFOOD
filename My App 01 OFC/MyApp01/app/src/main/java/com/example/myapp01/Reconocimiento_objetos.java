package com.example.myapp01;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class Reconocimiento_objetos extends AppCompatActivity {
    private static  final String TAG ="MyTag";
    ImageView tvPicture;
    TextView tvResult;
    Button btnChoosePicture;
    private static final int CAMERA_PERMISSION_CODE = 223;
    private static final int READ_STORAGE_PERMISSION_CODE = 144;
    private static final int WRITE_STORAGE_PERMISSION_CODE = 144;
    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;
    ImageButton btnimenu;
    private ImageButton usuariom;
    InputImage inputImage;
    ImageLabeler Labeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reconocimiento_objetos);
        tvPicture=findViewById(R.id.tvPicture);
        tvResult=findViewById(R.id.tvResult);
        btnChoosePicture=findViewById(R.id.btnChoosePicture);
        btnimenu= (ImageButton) findViewById(R.id.imageButton);
        usuariom= (ImageButton) findViewById(R.id.btnusuario);

        usuariom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reconocimiento_objetos.this,Userd.class));
            }
        });

        btnimenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Reconocimiento_objetos.this,Menu.class));
            }
        });

        Labeler= ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        cameraLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data=result.getData();
                        try {
                            Bitmap photo=(Bitmap) data.getExtras().get("data");
                            tvPicture.setImageBitmap(photo);
                            inputImage=InputImage.fromBitmap(photo,0);

                            processImage();
                        }catch (Exception e){
                            Log.d(TAG, "onActivityResult: "+e.getMessage());
                        }
                    }
                }
        );

        galleryLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data= result.getData();
                        try {
                            inputImage= InputImage.fromFilePath(Reconocimiento_objetos.this,data.getData());
                            tvPicture.setImageURI(data.getData());
                            processImage();
                        }catch (Exception e){
                            Log.d(TAG, "onActivityResult: "+e.getMessage());
                        }
                    }
                }
        );

        btnChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] options={"camera","gallery"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Reconocimiento_objetos.this);
                builder.setTitle("Pick a Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraLauncher.launch(cameraIntent);
                        }else{
                            Intent storageIntent=new Intent();
                            storageIntent.setType("image/*");
                            storageIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryLauncher.launch(storageIntent);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
    }

    private void processImage(){
        Labeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        String result="";
                        for(ImageLabel label : imageLabels){
                            result=result+"\n"+label.getText();
                        }
                        tvResult.setText(result);
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(Exception e){
                        Log.d(TAG,"onFailure: "+e.getMessage());
                    }
                });
    }

    public void checkPermission(String permission, int requestCode){
        if (ContextCompat.checkSelfPermission(Reconocimiento_objetos.this,permission)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Reconocimiento_objetos.this,new String[]{permission},requestCode);
        }else  {
            Toast.makeText(Reconocimiento_objetos.this, "Permission already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(Reconocimiento_objetos.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            } else {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_STORAGE_PERMISSION_CODE);
            }
        } else if(requestCode == READ_STORAGE_PERMISSION_CODE){
            if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(Reconocimiento_objetos.this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            } else{
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_STORAGE_PERMISSION_CODE);
            }
        } else if (requestCode == WRITE_STORAGE_PERMISSION_CODE){
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(Reconocimiento_objetos.this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
