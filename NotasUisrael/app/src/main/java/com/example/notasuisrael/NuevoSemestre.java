package com.example.notasuisrael;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.json.JSONObject;

public class NuevoSemestre extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    Spinner NuevoSemestre;
    TextView Usuario;
    ImageButton GuardarSemestre;
    Bundle NombreUsuario,CodigoUsuario;

    String SemestreSeleccionado="";
    Integer CodigoSemestreSeleccionado=0;
    Integer codigoUsuarioObtenido;
    String usuarioObtenido="";

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_semestre);
        Usuario=findViewById(R.id.txtdatosUsuarioMateria);
        GuardarSemestre=findViewById(R.id.btnguardarMateria);

        request= Volley.newRequestQueue(getApplicationContext());

        //SPINNER (COMBO-BOX)
        NuevoSemestre=findViewById(R.id.spnSemestresAsignados);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,
                R.array.combo_semestre,android.R.layout.simple_spinner_item);
        NuevoSemestre.setAdapter(adapter);
        NuevoSemestre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SemestreSeleccionado=parent.getItemAtPosition(position).toString();
                CodigoSemestreSeleccionado=parent.getSelectedItemPosition();
                if (SemestreSeleccionado.equals("Seleccione Semestre")){
                    GuardarSemestre.setEnabled(false);
                }else{
                    GuardarSemestre.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        NombreUsuario=getIntent().getExtras();
        CodigoUsuario=getIntent().getExtras();

        codigoUsuarioObtenido=CodigoUsuario.getInt("CODIGOUSUARIO");
        usuarioObtenido=NombreUsuario.getString("USUARIO");

        Usuario.setText(usuarioObtenido);

        GuardarSemestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebServiceSemestre();
            }
        });
    }
    private void cargarWebServiceSemestre() {
        String url = "http://"+ MainActivity.Global.ip+"/notasuisrael/registroSemestre.php?codigousuario="+codigoUsuarioObtenido+
                "&codigosemestre="+CodigoSemestreSeleccionado;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        Toast.makeText(getApplicationContext(),jsonObjectRequest.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"NO SE PUDO REGISTRAR SEMESTRE"+error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(),"SEMESTRE ASIGNADO",Toast.LENGTH_SHORT).show();
    }
}
