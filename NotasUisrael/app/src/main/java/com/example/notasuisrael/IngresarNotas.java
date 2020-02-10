package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class IngresarNotas extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{
    TextView DatosUsuario;
    EditText Seguimiento1,Examen1,Seguimiento2,Examen2,Supletorio;
    Spinner SemestresAsignados,MateriasAsignadas;
    ImageButton GuardarNotas;
    Button Menu;
    Bundle NombreUsuario,CodigoMateria,CodigoUsuario,CrearNotas;

    Integer codigoUsuarioObtenido;
    String usuarioObtenido="";
    String CrearNotasObtenido="";
    Integer WebSer=0;
    Integer codigoSemestre;
    Integer codigoMateria;
    Integer CodigoNota;

 Integer CodigoMateriaObtenido;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_notas);
        DatosUsuario=findViewById(R.id.txtdatosUsuarioNotas);
        SemestresAsignados=findViewById(R.id.spnSemestreNotas);
        MateriasAsignadas=findViewById(R.id.spnMateriaNotas);
        Seguimiento1=findViewById(R.id.edtseg1Notas);
        Examen1=findViewById(R.id.edtexa1Notas);
        Seguimiento2=findViewById(R.id.edtseg2Notas);
        Examen2=findViewById(R.id.edtexa2Notas);
        Supletorio=findViewById(R.id.edtsup);
        GuardarNotas=findViewById(R.id.btnGuardarNotas);
        Menu=findViewById(R.id.btnmenuNotas);




        NombreUsuario=getIntent().getExtras();
        CodigoMateria=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();
        CrearNotas=getIntent().getExtras();


        usuarioObtenido=NombreUsuario.getString("USUARIO");
        CodigoMateriaObtenido=CodigoMateria.getInt("CODIGOMATERIA");
        codigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");

        request= Volley.newRequestQueue(getApplicationContext());
        DatosUsuario.setText(usuarioObtenido);

        CargarWsSemestres();
        //Toast.makeText(getApplicationContext(),codigoUsuarioObtenido.toString(),Toast.LENGTH_SHORT).show();




        GuardarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarNotasWS();
            }
        });
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IngresarNotas.this,Menu.class);
                intent.putExtra("USUARIO",usuarioObtenido);
                intent.putExtra("CODIGOUSUARIO",codigoUsuarioObtenido);
                startActivity(intent);


            }
        });


    }
    private void GuardarNotasWS() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/ModificarNota.php?" +
                "seguimientop="+Seguimiento1.getText().toString()+
                "&examenp="+Examen1.getText().toString()+"&seguimientos="
                +Seguimiento2.getText().toString()+"&examens="
                +Examen2.getText().toString()+"&supletorio="
                +Supletorio.getText().toString()+"&codigonota="
                +CodigoNota.toString();
        jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=5;

    }




    private void CargarWsSemestres() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaUsuarioSemestre.php?codigousuario="+codigoUsuarioObtenido;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=2;
    }
    private void CargarWsMaterias() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaSemestreMateria.php?codigosemestre="+codigoSemestre;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=3;

    }
    private  void CargarWsNotas(){
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/consultaMateriaNotas.php?codigomateria="+codigoMateria;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        WebSer=4;
    }



    @Override
    public void onErrorResponse(VolleyError error) {
       if (WebSer==1){
            Toast.makeText(getApplicationContext(),"NO SE PUEDE CREAR LAS NOTAS"+error.toString(),Toast.LENGTH_SHORT).show();

        }
        if (WebSer==2){

        }
        Toast.makeText(getApplicationContext(),"error"+error.toString(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {
       if (WebSer==2){
           //request= Volley.newRequestQueue(getApplicationContext());

           Semestre semestre=null;
            ArrayList<String> comboSemestresList=new ArrayList<>();
            final  ArrayList<String> ComboSemestreListCodigo=new ArrayList<>();
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
            }catch (JSONException e){
                e.printStackTrace();
            }
           ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,comboSemestresList);
           SemestresAsignados.setAdapter(adapter);
           SemestresAsignados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   int num=parent.getSelectedItemPosition();
                   codigoSemestre=Integer.parseInt(ComboSemestreListCodigo.get(num));
                   CargarWsMaterias();

               }
               @Override
               public void onNothingSelected(AdapterView<?> parent) {


               }
           });

       }
        if (WebSer==3){
            Materias materias=null;
            ArrayList<String> comboMateriasList=new ArrayList<>();
            final ArrayList<String> ComboMateriaListCodigo=new ArrayList<>();
            JSONArray json =response.optJSONArray("sememate");
            try{
                for (int i=0;i<json.length();i++){
                    materias=new Materias();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);

                    materias.setDescripcionMateria(jsonObject.optString("descripcion_materia"));
                    materias.setCodigoMateria(jsonObject.optInt("codigo_materia"));
                    comboMateriasList.add(materias.getDescripcionMateria());
                    ComboMateriaListCodigo.add(materias.getCodigoMateria().toString());
                }
                ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,comboMateriasList);
                MateriasAsignadas.setAdapter(adapter);
                MateriasAsignadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int num=parent.getSelectedItemPosition();
                        codigoMateria=Integer.parseInt(ComboMateriaListCodigo.get(num));
                        CargarWsNotas();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        if (WebSer==4){
            Notas notas=new Notas();
            JSONArray json=response.optJSONArray("matenota");//trae el array con los datos
            JSONObject jsonObject=null;

            try {
                jsonObject = json.getJSONObject(0);
                notas.setCodigonota(jsonObject.optInt("codigo_nota"));

                notas.setSeguimiento1(jsonObject.optDouble("seguimientop_nota"));
                notas.setExamen1(jsonObject.optDouble("examenp_nota"));
                notas.setSeguimiento2(jsonObject.optDouble("seguimientos_nota"));
                notas.setExamen2(jsonObject.optDouble("examens_nota"));
                notas.setSupletorio(jsonObject.optDouble("supletorio_nota"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Double Sg1=notas.getSeguimiento1();
            Double Ex1=notas.getExamen1();
            Double Sg2=notas.getSeguimiento2();
            Double Ex2=notas.getExamen2();
            Double Su=notas.getSupletorio();

            Seguimiento1.setText(Sg1.toString());
            Examen1.setText(Ex1.toString());
            Seguimiento2.setText(Sg2.toString());
            Examen2.setText(Ex2.toString());
            Supletorio.setText(Su.toString());
            CodigoNota=notas.getCodigonota();
        }
        if (WebSer==5){
            Toast.makeText(getApplicationContext(),"NOTAS MODIFICADAS",Toast.LENGTH_SHORT).show();
        }



  }
}
