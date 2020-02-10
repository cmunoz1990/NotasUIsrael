package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    Button RecuperarSesion,RegistrarseSesion;
    ImageButton Inicio;
    EditText Correo,Password;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Correo=findViewById(R.id.edtcorreoSesion);
        Password=findViewById(R.id.edtpasswordSesion);
        RecuperarSesion=findViewById(R.id.btnrecuperarSesion);
        RegistrarseSesion=findViewById(R.id.btnregistrarseSesion);
        Inicio=findViewById(R.id.btningresarSesion);
        request= Volley.newRequestQueue(getApplicationContext());
        RecuperarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirActividad=new Intent(MainActivity.this, Pruebas.class);
                startActivity(abrirActividad);
            }
        });
        RegistrarseSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirActividad=new Intent(MainActivity.this, Registro.class);
                startActivity(abrirActividad);
            }
        });
       Inicio.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Ejecutar();

           }
       });

    }

    private void Ingresar(){
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://"+ Global.ip+"/notasuisrael/consultaUsuario.php?correo="+Correo.getText().toString();
        url=url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"Email invalido",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario miUsuario=new Usuario();
        JSONArray json=response.optJSONArray("usuario");//trae el array con los datos
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            miUsuario.setPasswordUsuario(jsonObject.optString("password_usuario"));
            miUsuario.setCodigoUsuario(jsonObject.optInt("codigo_usuario"));
            miUsuario.setNombreUsuario(jsonObject.optString("nombre_usuario"));
            miUsuario.setApellidoUsuario(jsonObject.optString("apellido_usuario"));
            miUsuario.setCarreraUsuario(jsonObject.optString("carrera_usuario"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String password=miUsuario.getPasswordUsuario();
        Integer codigo=miUsuario.getCodigoUsuario();
        String nombreusuario=miUsuario.getNombreUsuario()+" "+miUsuario.getApellidoUsuario();
        String carrerausurio=miUsuario.getCarreraUsuario();
        if (Password.getText().toString().equals(password)){
            Toast.makeText(this,"BIENVENIDO",Toast.LENGTH_SHORT).show();
            Intent abrirActividad=new Intent(MainActivity.this,Menu.class);
            abrirActividad.putExtra("CODIGOUSUARIO", codigo);
            abrirActividad.putExtra("USUARIO",nombreusuario);
            abrirActividad.putExtra("CARRERA",carrerausurio);
            startActivity(abrirActividad);
        }else {
            Toast.makeText(this,"PASSWORD INCORRECTO",Toast.LENGTH_SHORT).show();
        }


    }

    public static class Global {
        public static String ip="192.168.43.76";
    }
    //VALIDACIONES
    private void Ejecutar(){
        if(validarCamposLlenos()==true){
            validar();
        }else {
            validarCamposLlenos();
        }
    }
    //Validar email
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void validar(){
        if (!validarEmail(Correo.getText().toString())){
            Correo.setError("Email no valido");
        }else{
            Ingresar();
        }
    }
    //Validar campos llenos
    private boolean validarCamposLlenos(){
        boolean valor=false;
        if (Correo.length()==0){
            Correo.setError("Ingrese Email");
        }else if (Password.length()==0){
            Password.setError("Ingrese Password");
        }else
        {
            valor=true;
        }
        return valor;
    }




}
