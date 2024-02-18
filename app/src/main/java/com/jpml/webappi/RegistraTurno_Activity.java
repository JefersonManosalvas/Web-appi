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
    String respuesta = "", datos,Contra,NombreUsu;
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
         Contra=intent.getStringExtra("Pass");
         NombreUsu=intent.getStringExtra("Nomb");




        spi=findViewById(R.id.spinner);
        ConsultarAPI();

//        View rootView = findViewById(android.R.id.content);
//
//        rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.inicio) {
                    Intent intent = new Intent(RegistraTurno_Activity.this, Principal_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.agrega) {
                    Intent intent = new Intent(RegistraTurno_Activity.this, RegistraTurno_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.consultar) {
                    Intent intent = new Intent(RegistraTurno_Activity.this, ConsultaTurnos_Activity.class);
                    intent.putExtra("cedula", cedulas.getText().toString());
                    intent.putExtra("Usuario",usuario);
                    startActivity(intent);
                }
                return false;
            }
        });



    }


    public void ConsumirApi(View view) {
        String url = "http://192.168.1.111/ApisMovil/api.php?op=insTurnos&turn='"+spi.getSelectedItem()+"'&ced="+ced;;
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
                                Toast.makeText(RegistraTurno_Activity.this, "TURNO REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();

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

    public void ConsultarAPI() {

        String url = "http://192.168.1.111/ApisMovil/api.php?op=ConsultaDatos&usu=" +usuario+"&cla=" +clave;


            OkHttpClient cliente = new OkHttpClient();
            Request get = new Request.Builder()
                    .url(url)
                    .build();
            cliente.newCall(get).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        ResponseBody responseBody = response.body();
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);

                        } else {

                            respuesta = responseBody.string();
                            RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        if (respuesta.equals("[]")) {
                                            //Toast.makeText(RegistraTurno_Activity.this, "NO TIENE TURNOS GENERADOS PORFAVOR GENERE UN TURNO.", Toast.LENGTH_SHORT).show();
                                            ConsultarAPID();
                                        } else {
                                            JSONObject json = new JSONObject(respuesta);

                                            ced = json.getString("Cedula");
                                            cedulas.setText(ced);
                                            nombreComp.setText(json.getString("nombre_completo"));
                                            telefono.setText(json.getString("telefono"));
                                            APICBX();
                                        }
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



    public void ConsultarAPID() {

        String url = "http://192.168.1.111/ApisMovil/api.php?op=ConsultaDatos&usu=" +NombreUsu+"&cla=" +Contra;


        OkHttpClient cliente = new OkHttpClient();
        Request get = new Request.Builder()
                .url(url)
                .build();
        cliente.newCall(get).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);

                    } else {

                        respuesta = responseBody.string();
                        RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    if (respuesta.equals("[]")) {
                                        Toast.makeText(RegistraTurno_Activity.this, "NO ERROR AL PROCESAR SUS DATOS.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        JSONObject json = new JSONObject(respuesta);

                                        ced = json.getString("Cedula");
                                        cedulas.setText(ced);
                                        nombreComp.setText(json.getString("nombre_completo"));
                                        telefono.setText(json.getString("telefono"));
                                        APICBX();
                                    }
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






    public void APICBX() {
        String url = "http://192.168.1.111/ApisMovil/api.php?op=Servicios";

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
                    ResponseBody responseBody = response.body();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        respuesta = responseBody.string();
                        RegistraTurno_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = new JSONArray(respuesta);
                                    ArrayList<String> servicios = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String tipoServicio = jsonObject.getString("tipo_servicio");
                                        servicios.add(tipoServicio);
                                    }
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