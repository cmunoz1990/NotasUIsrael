package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ObtenerCodigoMateria extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    Bundle NombreUsuario,Materia,CodigoUsuario;
    Integer codigoUsuarioObtenido;
    String usuarioObtenido="";
    String MateriaObtenida="";
    Integer CodigoMateriaObtenida;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_codigo_materia);
        NombreUsuario=getIntent().getExtras();
        Materia=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();

        usuarioObtenido=NombreUsuario.getString("USUARIO");
        MateriaObtenida=Materia.getString("MATERIA");
        codigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");
        request= Volley.newRequestQueue(getApplicationContext());
        Toast.makeText(getApplicationContext(),MateriaObtenida,Toast.LENGTH_SHORT).show();
        wscargarCodigoMateria();
    }

    private void wscargarCodigoMateria(){
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaUltimaMateriaRegistrada.php?materia="+MateriaObtenida;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"NO SE PUEDE OBTENER EL CÓDIGO DE LA MATERIA"+error.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"consulta",Toast.LENGTH_SHORT).show();
        UltimaMateria ultimaMateria=new UltimaMateria();
        JSONArray json=response.optJSONArray("ultimamateria");
        //JSONObject jsonObject=null;
        try{
        JSONObject jsonObject=json.getJSONObject(0);

            //jsonObject=json.getJSONObject(0);
            ultimaMateria.setCodigoMateria(jsonObject.optInt("codigo_materia"));
            ultimaMateria.setDescripcionMatera(jsonObject.optString("descripcion_materia"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        CodigoMateriaObtenida=ultimaMateria.getCodigoMateria();
        String des=ultimaMateria.getDescripcionMatera();
        //Toast.makeText(getApplicationContext(),CodigoMateriaObtenida.toString(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ObtenerCodigoMateria.this,CrearNotas.class);
        intent.putExtra("CODIGOMATERIA",CodigoMateriaObtenida);
        intent.putExtra("USUARIO",usuarioObtenido);
        intent.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido);
        startActivity(intent);




    }
}
