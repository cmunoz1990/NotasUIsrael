package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CrearNotas extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Bundle NombreUsuario,CodigoMateria,CodigoUsuario;

    Integer CodigoMateriaObtenido;
    String NombreUsuarioObtenido;
    Integer CodigoUsuarioObtenido;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notas);
        CodigoMateria=getIntent().getExtras();
        NombreUsuario=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();

         CodigoMateriaObtenido=CodigoMateria.getInt("CODIGOMATERIA");
         NombreUsuarioObtenido=NombreUsuario.getString("USUARIO");
         CodigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");
        //Toast.makeText(getApplicationContext(),"cod: "+CodigoMateriaObtenido.toString(),Toast.LENGTH_SHORT).show();
        request= Volley.newRequestQueue(getApplicationContext());

        CrearNotas();

    }
    private void CrearNotas() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/registroNota.php?seguimientop=0.0&examenp=0.0&seguimientos=0.0" +
                "&examens=0.0&supletorio=0.0&codigomateria="+CodigoMateriaObtenido;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"NOOOOO se graban los datos de notas",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"si se graban los datos de notas",Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(CrearNotas.this,IngresarNotas.class);
        intent.putExtra("CODIGOMATERIA",CodigoMateriaObtenido);
        intent.putExtra("USUARIO",NombreUsuarioObtenido);
        intent.putExtra("CODIGOUSUARIO",CodigoUsuarioObtenido);
        startActivity(intent);

    }
}
