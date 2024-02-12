package com.jpml.webappi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ConsultaTurnos_Activity extends AppCompatActivity {

    TextView nombreCom,numTurno,fecha,Tservicio;

    String respuesta,Cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_turnos);

        nombreCom=findViewById(R.id.txtcliente);
        numTurno=findViewById(R.id.txtNTurno);
        fecha=findViewById(R.id.txtFecha);
        Tservicio=findViewById(R.id.txtServicio);

        Intent intent = getIntent();
         Cedula = intent.getStringExtra("cedula");
        // nombreCom.setText(Cedula);
        ConsultarAPI();



        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.inicio) {
                   // Toast.makeText(Principal_Activity.this,"A SELECCIONADO LA OPCION 1",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ConsultaTurnos_Activity.this,Principal_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId()==R.id.agrega) {
                    Intent intent = new Intent(ConsultaTurnos_Activity.this,RegistraTurno_Activity.class);
                    startActivity(intent);
                } else if (item.getItemId()==R.id.consultar) {
                    Intent intent = new Intent(ConsultaTurnos_Activity.this, ConsultaTurnos_Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }



    public void ConsultarAPI() {
        String url = "http://192.168.1.106/ApisMovil/api.php?op=DatosTurno&ced="+Cedula;
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
                        ConsultaTurnos_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(respuesta);
                                    String fechaCompleta = json.getString("fecha");
                                    String fechaSinHora = fechaCompleta.substring(0, fechaCompleta.indexOf(' '));
                                    fecha.setText(fechaSinHora);
                                    numTurno.setText(json.getString("numero_turno"));
                                    nombreCom.setText(json.getString("nombre_completo"));
                                    Tservicio.setText(json.getString("tipo_servicio"));
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


}