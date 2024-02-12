package com.jpml.webappi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import android.view.View;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegistraTurno_Activity extends AppCompatActivity {

    TextView cedulas,nombreComp,telefono;
    String respuesta = "", datos;
    Spinner spi;
    String usuario,clave,ced;
    Button guarda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_turno);
        cedulas = findViewById(R.id.edCedulas);
        nombreComp = findViewById(R.id.edNombreYApellido);
        telefono=findViewById(R.id.edTelefonoUsu);
        guarda=findViewById(R.id.btnInsert);


        Intent intent = getIntent();
         usuario = intent.getStringExtra("usuario");
         clave = intent.getStringExtra("clave");


        spi=findViewById(R.id.spinner);
        ConsultarAPI();

        View rootView = findViewById(android.R.id.content);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APICBX();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.inicio) {
                    // Toast.makeText(Principal_Activity.this,"A SELECCIONADO LA OPCION 1",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegistraTurno_Activity.this, Principal_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.agrega) {
                    Intent intent = new Intent(RegistraTurno_Activity.this, RegistraTurno_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.consultar) {
                    Intent intent = new Intent(RegistraTurno_Activity.this, ConsultaTurnos_Activity.class);
                    intent.putExtra("cedula", cedulas.getText().toString());
                    startActivity(intent);
                }
                return false;
            }
        });



    }


    public void ConsumirApi(View view) {
        //String url="https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a="+v1.getText()+"&b="+v2.getText();
        String url = "http://192.168.1.108/ApisMovil/api.php?op=insTurnos&ced="+ced;


        OkHttpClient cliente = new OkHttpClient();

        Request get = new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    ResponseBody responseBody = response.body();
                    if (response.isSuccessful()) {

                        respuesta = responseBody.string();
                        RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //  res1.setText(respuesta);
                                Toast.makeText(RegistraTurno_Activity.this, "TURNO REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();

//                                if (respuesta.equals("2")) {
//                                    //Toast.makeText(MainActivity.this, "Usuario correcto", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(RegistraTurno_Activity.this, Principal_Activity.class);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(RegistraTurno_Activity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
//                                }
                            }
                        });
                    } else {
                        throw new IOException("Respuesta inesperada" + response);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
//
    public void ConsultarAPI() {
        //DIRECCIÓN URL DEL SERVICIO A CONSUMIR
        String url = "http://192.168.1.108/ApisMovil/api.php?op=ConsultaDatos&usu=" +usuario+"&cla=" +clave;
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
                            /*tvResultado.setText(respuesta);
                            Toast.makeText(MainActivity.this,"El resultado es:"+respuesta,Toast.LENGTH_LONG).show();*/
                                try {
                                    JSONObject json = new JSONObject(respuesta);

                                    ced=json.getString("Cedula");
                                    cedulas.setText(ced);
                                    nombreComp.setText(json.getString("nombre_completo"));
                                    telefono.setText(json.getString("telefono"));
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }


                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//
////            public void guardaInicio(View view){
////                Intent intent =new Intent(RegistraTurno_Activity.this, Principal_Activity.class);
////                startActivity(intent);
////            }
//
//
//    }


    public void APICBX() {
        // Dirección URL del servicio a consumir
        String url = "http://192.168.1.108/ApisMovil/api.php?op=Servicios";

        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder().url(url).build();

        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    // Obtenemos la respuesta
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        respuesta = responseBody.string();

                        // Procesamos la respuesta en el hilo principal
                        RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Parseamos el JSON
                                    JSONArray jsonArray = new JSONArray(respuesta);

                                    // Creamos un ArrayList para almacenar los datos del Spinner
                                    ArrayList<String> servicios = new ArrayList<>();

                                    // Iteramos sobre el JSONArray y añadimos los datos al ArrayList
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String tipoServicio = jsonObject.getString("tipo_servicio");
                                        servicios.add(tipoServicio);
                                    }

                                    // Creamos un ArrayAdapter y lo establecemos en el Spinner
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegistraTurno_Activity.this, android.R.layout.simple_spinner_item, servicios);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spi.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}