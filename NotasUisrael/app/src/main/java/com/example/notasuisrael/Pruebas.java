package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pruebas extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    TextView Retorno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
        /*Codigocampo=findViewById(R.id.txtcodigo);
        Nombrecampo=findViewById(R.id.txtnombre);
        Apellidocampo=findViewById(R.id.txtapellido);
        Guardar=findViewById(R.id.btnguardar);
        Retorno=findViewById(R.id.txtres);
        corr=findViewById(R.id.txtemail);

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wscargar();
            }
        });*/
        Retorno=findViewById(R.id.txtres);
        request= Volley.newRequestQueue(getApplicationContext());
        wscargar();

    }
    private void wscargar(){
        String materia="EEFF";
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaUltimaMateriaRegistrada.php?materia="+materia;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"NO SE PUEDE OBTENER EL CÓDIGO DE LA MATERIA"+error.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        UltimaMateria ultimaMateria=new UltimaMateria();
        JSONArray json=response.optJSONArray("ultimamateria");
        JSONObject jsonObject=null;
        try{
            jsonObject=json.getJSONObject(0);
            ultimaMateria.setCodigoMateria(jsonObject.optInt("codigo_materia"));
            ultimaMateria.setDescripcionMatera(jsonObject.optString("descripcion_materia"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        Integer codigoMateria=ultimaMateria.getCodigoMateria();
        String des=ultimaMateria.getDescripcionMatera();
        Retorno.setText(codigoMateria.toString());
//        Toast.makeText(getApplicationContext(),codigoMateria,Toast.LENGTH_SHORT).show();

    }
}
