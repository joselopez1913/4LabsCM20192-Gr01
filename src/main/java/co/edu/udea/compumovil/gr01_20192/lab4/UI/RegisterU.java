package co.edu.udea.compumovil.gr01_20192.lab4.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import co.edu.udea.compumovil.gr01_20192.lab4.DataBase.UserDB;
import co.edu.udea.compumovil.gr01_20192.lab4.Entities.User;
import co.edu.udea.compumovil.gr01_20192.lab4.R;

public class RegisterU extends AppCompatActivity {

    private Button buttontoLogin;
    Button btnGrabarUsu;
    EditText txtUser,txtEmail,txtPassword;

    private UserDB UDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //1. Boton Atras Intent a pantalla de login

        buttontoLogin = findViewById(R.id.backRegisterButton);
        buttontoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openActivityLogin();
            }
        });
        //1.

        //2. registro usuarios en BD
        btnGrabarUsu=(Button)findViewById(R.id.bRegisterButton);
        txtUser=(EditText)findViewById(R.id.userRegisterText);
        txtEmail=(EditText)findViewById(R.id.emailRegisterText);
        txtPassword=(EditText)findViewById(R.id.passwordRegisterText);

        UDB= UserDB.getAppDatabase(getApplicationContext());

        btnGrabarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insertar a la BD

                UDB.userDao().insertAll(new User(txtUser.getText().toString(),txtEmail.getText().toString(),txtPassword.getText().toString()));

                Toast.makeText(getApplicationContext(), "Usuario Registrado"
                ,Toast.LENGTH_LONG).show();

                Intent i=new Intent(getApplicationContext(),LoginU.class);
                startActivity(i);
            }
        });
        //2.

    }


    //1.2
    public void openActivityLogin(){
        Intent intent = new Intent(this, LoginU.class);
        startActivity(intent);
    }
    //1.2
}

