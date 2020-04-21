package co.edu.udea.compumovil.gr01_20192.lab4.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import co.edu.udea.compumovil.gr01_20192.lab4.R;


public class AddSite extends AppCompatActivity {

    EditText edtName, edtDesc, edtPoint;
    Button btnChoose, btnAdd, btnList;
    ImageView imageView;

    private DatabaseReference mDatabase;

    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

     edtName=(EditText)findViewById(R.id.nameSite);
     edtDesc = (EditText)findViewById(R.id.descripSite);
     edtPoint= (EditText)findViewById(R.id.pointSite);
     imageView=(ImageView) findViewById(R.id.imageView3);

     btnChoose = (Button) findViewById(R.id.chooseButton);
     btnAdd = (Button) findViewById(R.id.addButton);
     btnList = (Button) findViewById(R.id.listButton);


     btnAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String name=edtName.getText().toString();
             String description=edtDesc.getText().toString();
             String point=edtPoint.getText().toString();

             if(name.isEmpty()) {
                 addPoi(name, description, point);

                 Intent intent = new Intent(getApplicationContext(), MenuU.class);
                 startActivity(intent);
             }
             Toast.makeText(AddSite.this, "No ha ingresado ningún sitio de interes", Toast.LENGTH_SHORT).show();

         }
     });
     mDatabase = FirebaseDatabase.getInstance().getReference();


/*
     //open gallery and select pic
     btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddSite.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
     });

 */


     //go to menu activity
     btnList = findViewById(R.id.listButton);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MenuU.class);
                startActivity(intent);
            }
        });

    }

    //add POI to Database
    private void addPoi(String name, String description, String point) {
        Map<String, Object> datosPoi = new HashMap<>();
        datosPoi.put("sitio" ,name);
        datosPoi.put("descripcion" ,description);
        datosPoi.put("puntos", point);
        datosPoi.put("imagen","https://firebasestorage.googleapis.com/v0/b/lab4fcm-a9957.appspot.com/o/Pagina_en_construccion.jpg?alt=media&token=ac5fdceb-a2e2-4ed5-aa9f-d299a7eb8e79");

        mDatabase.child("Poi").push().setValue(datosPoi);
    }

    //convert image
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //permisions gallery
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "No tienes permiso para acceder a galeria", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //add pic
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

