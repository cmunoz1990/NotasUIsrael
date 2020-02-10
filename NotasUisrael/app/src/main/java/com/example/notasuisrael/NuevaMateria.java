package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;

public class NuevaMateria extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    TextView Usuario;
    EditText Materia;
    ImageButton GuardarMateria;
    Bundle NombreUsuario,CodigoUsuario,CarreraUsuario,NotasCreadas;
    Spinner SemestresAsignados;

    Integer codigoUsuarioObtenido;
    String NombreUsuarioObtenido="";
    Integer codigoSemestreObtenido;
    Integer codigoMateriaObtenido;

    //obtener codigo semestre
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //Guardar materia

    int WebSer=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_materia);
        Usuario=findViewById(R.id.txtdatosUsuarioMateria);
        SemestresAsignados=findViewById(R.id.spnSemestresAsignados);
        GuardarMateria=findViewById(R.id.btnguardarMateria);
        Materia=findViewById(R.id.edtmateriaNueva);


        NombreUsuario=getIntent().getExtras();
        CarreraUsuario=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();


        codigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");
        NombreUsuarioObtenido=NombreUsuario.getString("USUARIO");

        Usuario.setText(NombreUsuarioObtenido);
        request= Volley.newRequestQueue(getApplicationContext());

        CargarWsSemestres();

        GuardarMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 CargarWSMateria();
            }
        });

    }

    private void CargarWSMateria(){
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/registroMateria.php?materia="+Materia.getText().toString()+
                "&codigosemestre="+codigoSemestreObtenido;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=2;

    }

    private void CargarWsSemestres() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaUsuarioSemestre.php?codigousuario="+codigoUsuarioObtenido;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=1;

    }
    private void ObtenerCodigoMateriaWS(){
        Intent abrirActividad= new Intent(NuevaMateria.this,ObtenerCodigoMateria.class);
        abrirActividad.putExtra("USUARIO",NombreUsuarioObtenido);
        abrirActividad.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido);
        abrirActividad.putExtra("MATERIA",Materia.getText().toString());

        startActivity(abrirActividad);
        Toast.makeText(getApplicationContext(),Materia.getText().toString(),Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onErrorResponse(VolleyError error) {
        if (WebSer==1){
            Toast.makeText(getApplicationContext(),"NO SE PUEDE OBTENER SEMESTRE SEMESTRE"+error.toString(),Toast.LENGTH_SHORT).show();
        }
        if(WebSer==2){
            Toast.makeText(getApplicationContext(),"NO SE PUEDE CARGAR LA MATERIA"+error.toString(),Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onResponse(JSONObject response) {
        if (WebSer==1){
            Semestre semestre=null;
            ArrayList<String> comboSemestresList=new ArrayList<>();
            final ArrayList<String> ComboSemestreListCodigo=new ArrayList<>();
            JSONArray json =response.optJSONArray("ususeme");
            try{
                for (int i=0;i<json.length();i++){
                    semestre=new Semestre();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    semestre.setDescripcionSemestre(jsonObject.optString("descripcion_semestre"));
                    semestre.setCodigoSemestre(jsonObject.optInt("codigo_semestre"));
                    comboSemestresList.add(semestre.getDescripcionSemestre());
                    ComboSemestreListCodigo.add(semestre.getCodigoSemestre().toString());
                }
                ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,comboSemestresList);
                SemestresAsignados.setAdapter(adapter);
                SemestresAsignados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int num=parent.getSelectedItemPosition();
                        codigoSemestreObtenido=Integer.parseInt(ComboSemestreListCodigo.get(num));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        if(WebSer==2){
            //Toast.makeText(getApplicationContext(),"MATERIA GRABADA",Toast.LENGTH_SHORT).show();
            ObtenerCodigoMateriaWS();


        }
    }
}
