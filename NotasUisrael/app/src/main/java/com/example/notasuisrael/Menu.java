package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    Button NuevoSemestre,NuevaMateria,NuevaNota;
    TextView UsuarioResivido;
    Bundle NombreUsuario,CodigoUsuario,CarreraUsuario;
    Integer codigoUsuarioObtenido;
    String usuarioObtenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       NuevoSemestre=findViewById(R.id.btnnuevoSemestreMenu);
       UsuarioResivido=findViewById(R.id.txtusuariomenu);
        NombreUsuario=getIntent().getExtras();
        CarreraUsuario=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();
        NuevaMateria=findViewById(R.id.btnnuevaMateriaMenu);
        NuevaNota=findViewById(R.id.btnnuevaNotaMenu);

        codigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");
        usuarioObtenido =NombreUsuario.getString("USUARIO");

        UsuarioResivido.setText(usuarioObtenido);

       NuevoSemestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirActividad= new Intent(Menu.this,NuevoSemestre.class);
                abrirActividad.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido );
                abrirActividad.putExtra("USUARIO",usuarioObtenido);
              startActivity(abrirActividad);
            }
        });
       NuevaMateria.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent abrirActividad= new Intent(Menu.this,NuevaMateria.class);
               abrirActividad.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido );
               abrirActividad.putExtra("USUARIO",usuarioObtenido);
               startActivity(abrirActividad);
           }
       });
       NuevaNota.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent abrirActividad= new Intent(Menu.this,IngresarNotas.class);
               abrirActividad.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido );
               abrirActividad.putExtra("USUARIO",usuarioObtenido);
               startActivity(abrirActividad);
           }
       });
    }
}
