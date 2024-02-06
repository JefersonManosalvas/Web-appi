package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.util.Calendar;
import android.view.View;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class RegistraTurno_Activity extends AppCompatActivity {
    EditText cedula,nombre;
    TextView Fecha;
    String respuesta = "", datos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_turno);
        cedula=findViewById(R.id.edCedula2);
        nombre=findViewById(R.id.edNombres2);
        Fecha=findViewById(R.id.txtFecha2);
        ConsumirAPI();
    }


    public void ConsumirAPI() {
        //DIRECCIÓN URL DEL SERVICIO A CONSUMIR
        String url = "http://192.168.1.106/coreccion1/wvs/wapi.php?op=con&id=1004656979";
        //OBJETO PARA EL USO DE PROTOCOLO HTTP
        OkHttpClient cliente = new OkHttpClient();
        //CONSTRUIMOS EL REQUERIMIENTO DEL TIPO DE API (GET,POST,PUT, DELETE)
        Request get = new Request.Builder()
                .url(url)
                .build();
        //A TRAVÉS DE OKHTTP LLAMAMOS AL SERVICIO Y ENCOLAMOS LAS PETICIONES
        cliente.newCall(get).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    //OBTENEMOS LA RESPUESTA
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    } else {

                        respuesta = responseBody.string();

                        // A TRAVÉS DEL USO DE HILOS PARALELAMENTE A LA CONSULTA DEL SERVIDOR MOSTRAMOS LA RESPUESTA
                        RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //tvResultado.setText(respuesta);
                                //Toast.makeText(Principal_Activity.this,"El resultado es:"+respuesta,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject json = new JSONObject(respuesta);
//                                    datos = json.getString("Cedula");
//                                    Toast.makeText(RegistraTurno_Activity.this, "" + datos, Toast.LENGTH_SHORT).show();
////                                    nombre.setText(json.getString("userId"));
//                                    telefono.setText(json.getString("id"));
                                    cedula.setText(json.getString("Cedula"));
                                    nombre.setText(json.getString("usu_nombre")+" "+json.getString("usu_apellido"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        });
                    }


                    // Log.i("data", responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




    }


    public void AbrirCalendario(View view){
        Calendar cal= Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        int mes= cal.get(Calendar.MONTH);
        int dia= cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd=new DatePickerDialog(RegistraTurno_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha= dayOfMonth+"/"+(month+1)+"/"+year;
                Fecha.setText(fecha);
            }
        },dia,mes,anio);
        dpd.show();
}


            public void guardaInicio(View view){
                Intent intent =new Intent(RegistraTurno_Activity.this, Principal_Activity.class);
                startActivity(intent);
            }












}