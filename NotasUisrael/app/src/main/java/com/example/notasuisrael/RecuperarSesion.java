package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class RecuperarSesion extends AppCompatActivity {
    EditText Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_sesion);
        Email=findViewById(R.id.edtcorreoRecuperar);

    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void validar(View v){
        if (!validarEmail(Email.getText().toString())){
            Email.setError("Email no válido");
        }
    }

}
