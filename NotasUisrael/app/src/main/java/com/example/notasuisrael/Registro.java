package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Registro extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText NombreRegistro,ApellidoRegistro,CarreraRegistro,CorreoRegistro;
    EditText Password1Registro,Password2Registro,Codigo;
    TextView TextoRegistro;
    Button GuardarRegistro,ValidarRgistro;
    ImageView Previsualizar;
    String currentPhotoPath;
    ImageButton btnCamara;

    String correo;
    String contrasena;
    Session session;
    String CodigoValidacion="000000";

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String Foto="path de fotografia",Validar="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        NombreRegistro=findViewById(R.id.edtnombreregistro);
        ApellidoRegistro=findViewById(R.id.edtapellidoregistro);
        CarreraRegistro=findViewById(R.id.edtcarreraregistro);
        CorreoRegistro=findViewById(R.id.edtcorreoregistro);
        Password1Registro=findViewById(R.id.edtpasswrod1registro);
        Password2Registro=findViewById(R.id.edtpassword2registro);
        GuardarRegistro=findViewById(R.id.btnguardarregistro);
        Previsualizar=findViewById(R.id.imvfoto);
        btnCamara=findViewById(R.id.btnfoto);
        Codigo=findViewById(R.id.edtCodigoValidacionRegistro);
        TextoRegistro=findViewById(R.id.txtvalidarRegistro);
        ValidarRgistro=findViewById(R.id.btnvalidarRegistro);

        Codigo.setVisibility(View.INVISIBLE);
        TextoRegistro.setVisibility(View.INVISIBLE);
        ValidarRgistro.setVisibility(View.INVISIBLE);

        //correo
            correo="graipod06@gmail.com";
            contrasena="1718269168";

        //fin correo


        request= Volley.newRequestQueue(getApplicationContext());
        GuardarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ejecutar();
            }
        });
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TomarFoto();
                //correoElectronicoSend();

            }
        });
        ValidarRgistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Codigo.getText().toString().equals(CodigoValidacion)){
                    cargarWebService();
                }else{
                    Toast.makeText(getApplicationContext(),"CÓDIGO INCORRECTO",Toast.LENGTH_LONG).show();
                }
            }
        });
        //HABILITA PERMISOS DESDE EL CONTROLADOR
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

    }
    private void cargarWebService() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/" +
                "registroUsuario.php?nombre="
                + NombreRegistro.getText().toString() +
                "&apellido=" + ApellidoRegistro.getText().toString() +
                "&carrera=" + CarreraRegistro.getText().toString() +
                "&correo=" + CorreoRegistro.getText().toString() +
                "&password=" + Password1Registro.getText().toString() +
                "&foto=gffgfg&validar=0";
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, this, this);
        request.add(jsonObjectRequest);
    }

  @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se pudo registrar"+error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();

        NombreRegistro.setText("");
        ApellidoRegistro.setText("");
        CarreraRegistro.setText("");
        CorreoRegistro.setText("");
        Password1Registro.setText("");
        Password2Registro.setText("");


    }
    //CODIGO PARA FOTO

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //VISTA PREVIA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Previsualizar.setImageBitmap(imageBitmap);
        }
    }


    //GENERA EL NOMBRE ÚNICO DE CADA FOTOGRAFÍA
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    private void TomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    //VALIDACIONES
    //Validar email
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void validar(){
        if (!validarEmail(CorreoRegistro.getText().toString())){
            CorreoRegistro.setError("Email no valido");
            Toast.makeText(this,"Email No Valido",Toast.LENGTH_SHORT).show();
        }else{
           /* String email = CorreoRegistro.getText().toString();
            String key = "ev-827fcc8b85354a79464208d0897a5929";*/
                if (Password1Registro.getText().toString().equals(Password2Registro.getText().toString())){
                    Codigo.setVisibility(View.VISIBLE);
                    TextoRegistro.setVisibility(View.VISIBLE);
                    ValidarRgistro.setVisibility(View.VISIBLE);
                    correoElectronicoSend();
                }else{
                    Toast.makeText(this,"Las contraseñas NO coinciden",Toast.LENGTH_SHORT).show();
                    Password2Registro.setError("Password no coincide");
                }
        }
    }
    //Validar campos llenos
    private boolean validarCamposLlenos(){
        boolean valor=false;
        if (NombreRegistro.length()==0){
            NombreRegistro.setError("Ingrese un nombre");
            Toast.makeText(this,"Ingrese un nombre",Toast.LENGTH_SHORT).show();
        }else if(ApellidoRegistro.length()==0){
            ApellidoRegistro.setError("Ingrese un Apellido");
            Toast.makeText(this,"Ingrese un Apellido",Toast.LENGTH_SHORT).show();
        }else if(CorreoRegistro.length()==0){
            CorreoRegistro.setError("Ingrese un Correo");
            Toast.makeText(this,"Ingrese un Correo",Toast.LENGTH_SHORT).show();
        }else if (CarreraRegistro.length()==0){
            CarreraRegistro.setError("Ingrese una carrera");
            Toast.makeText(this,"Ingrese una carrera",Toast.LENGTH_SHORT).show();
        }else if(Password1Registro.length()==0){
            Password1Registro.setError("Ingrese una contraseña");
            Toast.makeText(this,"Ingrese una contraseña",Toast.LENGTH_SHORT).show();
        }else if(Password2Registro.length()==0){
            Password2Registro.setError("Vuelva a ingresar la contraseña");
            Toast.makeText(this,"Vuelva a ingresar la contraseña",Toast.LENGTH_SHORT).show();
        }else {
            valor=true;
        }
        return valor;
    }

    private void Ejecutar(){
        if(validarCamposLlenos()==true){
            validar();
        }else {
            validarCamposLlenos();
        }
    }

    //FUNCION CORREO ELECTRÓNICO
    public void correoElectronicoSend(){
        Random r = new Random();
        int limite=1000;
        int codigoaleatorio=(r.nextInt(limite+1));
        CodigoValidacion=Integer.toString(codigoaleatorio);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.googlemail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
        try{
            session=Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,contrasena);
                }
            });
            if (session!=null){
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Código de verificación");
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse
                        (CorreoRegistro.getText().toString()));
                message.setContent("Si código de verificación es: \n"+CodigoValidacion,
                        "text/html; charset=utf-8");
                Transport.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
