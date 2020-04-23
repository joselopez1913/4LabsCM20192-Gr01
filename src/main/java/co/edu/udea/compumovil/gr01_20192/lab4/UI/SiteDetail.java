package co.edu.udea.compumovil.gr01_20192.lab4.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.edu.udea.compumovil.gr01_20192.lab4.R;

public class SiteDetail extends AppCompatActivity {

    TextView sitio, descripcion, puntos;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_poi);

        imagen=findViewById(R.id.imagePOI22);
        sitio=findViewById(R.id.txt11);
        descripcion=findViewById(R.id.txt22);
        puntos=findViewById(R.id.txt33);


        //load info in fragments details
        Intent i=getIntent();
        Picasso.get().load(i.getStringExtra("imagen")).into(imagen);
        sitio.setText(i.getStringExtra("sitio"));
        descripcion.setText(i.getStringExtra("descripcion"));
        puntos.setText(i.getStringExtra("puntos"));


    }
}
