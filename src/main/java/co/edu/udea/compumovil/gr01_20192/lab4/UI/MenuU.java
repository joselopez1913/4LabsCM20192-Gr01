package co.edu.udea.compumovil.gr01_20192.lab4.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01_20192.lab4.Entities.Poi;
import co.edu.udea.compumovil.gr01_20192.lab4.R;



public class MenuU extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button buttonnew, buttons;


    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    FirebaseDatabase database;
    DatabaseReference ref;

    ListView lvPoi, listView;
    private ArrayList<Poi> listpoi,lp;
    private ArrayList<String> list;
    private ArrayAdapter<Poi> adapter;
    AdapterPoi adapterPoi;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuu);

        // config menu
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_settings);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_signout)
                {
                    Toast.makeText(getApplicationContext(), "Sesión Finalizada", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginU.class);
                    startActivity(intent);

                }
                else if(item.getItemId()== R.id.action_settings)
                {
                    Toast.makeText(getApplicationContext(), "Intentelo mas tarde", Toast.LENGTH_SHORT).show();
                    // Intent intent = new Intent(MenuU.this, SettingsFragment.class);
                    //startActivity(intent);
                }
                else{
                    // do something
                }
                return false;
            }
        });




        //read DB
        lvPoi = (ListView) findViewById(R.id.LvPoi);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Poi");
        listpoi = new ArrayList<>();
        adapter = new ArrayAdapter<Poi>(this, android.R.layout.simple_selectable_list_item, listpoi);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String namepoi = ds.child("sitio").getValue().toString();
                    String descpoi = ds.child("descripcion").getValue().toString();
                    String pointpoi = ds.child("puntos").getValue().toString();
                    String imagepoi = ds.child("imagen").getValue().toString();

                    Poi p =new Poi();
                    p.setNamep(namepoi);
                    p.setDescription(descpoi);
                    p.setPoint(pointpoi);
                    p.setImage(imagepoi);
                    listpoi.add(p);
                }
                adapterPoi = new AdapterPoi(MenuU.this);
                lvPoi.setAdapter(adapterPoi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //fragment with info
        lp = listpoi;
        listView=(ListView)findViewById(R.id.LvPoi);
        AdapterPoi aa = new AdapterPoi(this);
        listView.setAdapter(aa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SiteDetail.class);

                intent.putExtra("imagen",lp.get(position).getImage());
                intent.putExtra("sitio",lp.get(position).getNamep());
                intent.putExtra("descripcion",lp.get(position).getDescription());
                intent.putExtra("puntos",lp.get(position).getPoint());

                startActivity(intent);
            }
        });

        //floating button new site
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddSite.class);
                startActivity(intent);
            }
        });

        //with Google auth
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                   // Log.i(TAG, "1");
                   // goLogInScreen();
                }
            }
        };
    }



    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LoginU.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }



    private void setSupportActionBar(Toolbar myToolbar) {
    }




    class AdapterPoi extends ArrayAdapter<Poi>{

        AppCompatActivity appCompatActivity;

        public AdapterPoi(AppCompatActivity context){
            super(context,R.layout.listpoi,listpoi);
            appCompatActivity=context;
        }

        public View getView(int position, View convertviw, ViewGroup parent){
            LayoutInflater inflater=appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.listpoi,null);

            ImageView image3 =  (ImageView)item.findViewById(R.id.imagePOI);
            TextView name3 = item.findViewById(R.id.txtName);
            TextView desc3 = item.findViewById(R.id.txtDesc);
            TextView point3 = item.findViewById(R.id.txtPoint);

            name3.setText(listpoi.get(position).getNamep());
            desc3.setText(listpoi.get(position).getDescription());
            point3.setText(listpoi.get(position).getPoint());
            Picasso.get().load(listpoi.get(position).getImage()).into(image3);


            return item;
        }

    }
}



