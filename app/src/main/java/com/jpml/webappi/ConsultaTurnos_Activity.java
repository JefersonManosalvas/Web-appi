package com.jpml.webappi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    TextView nombreCom, numTurno, fecha, Tservicio;
    String respuesta, Cedula, Usuario, usu, pass;
    Button cancela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_turnos);

        // Inicialización de vistas
        nombreCom = findViewById(R.id.txtcliente);
        numTurno = findViewById(R.id.txtNTurno);
        fecha = findViewById(R.id.txtFecha);
        Tservicio = findViewById(R.id.txtServicio);
        cancela = findViewById(R.id.btnCancelar);


        // Consultar la API
        //ConsultarAPI();


        // Configuración del botón de cancelar
        cancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    // Método para mostrar un diálogo de confirmación
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta")
                .setMessage("¿Está seguro de cancelar el turno?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //ConsumirApiET();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public  void home(View view){
        Intent intent = new Intent(ConsultaTurnos_Activity.this, Principal_Activity.class);
        startActivity(intent);
        finish();
    }

    public  void agregar(View view){
        Intent intent = new Intent(ConsultaTurnos_Activity.this, RegistraTurno_Activity.class);
        startActivity(intent);

    }

}
//
//    // Método para consultar la API y obtener los datos del turno
//    public void ConsultarAPI() {
//        String url = "http://192.168.1.111/ApisMovil/api.php?op=DatosTurno&ced=" + Cedula;
//        OkHttpClient cliente = new OkHttpClient();
//        Request get = new Request.Builder()
//                .url(url)
//                .build();
//        cliente.newCall(get).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ConsultaTurnos_Activity.this, "Error al consultar la API", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    ResponseBody responseBody = response.body();
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    } else {
//                        respuesta = responseBody.string();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    if (respuesta.equals("[]")) {
//                                        Toast.makeText(ConsultaTurnos_Activity.this, "NO TIENE TURNOS GENERADOS PORFAVOR GENERE UN TURNO.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        JSONObject json = new JSONObject(respuesta);
//                                        if (json.has("error")) {
//                                            String errorMessage = json.getString("error");
//                                            Toast.makeText(ConsultaTurnos_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            String fechaCompleta = json.getString("fecha");
//                                            String fechaSinHora = fechaCompleta.substring(0, fechaCompleta.indexOf(' '));
//                                            fecha.setText(fechaSinHora);
//                                            numTurno.setText(json.getString("numero_turno"));
//                                            nombreCom.setText(json.getString("nombre_completo"));
//                                            Tservicio.setText(json.getString("tipo_servicio"));
//                                        }
//                                    }
//                                } catch (JSONException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    // Método para consumir la API y cancelar el turno
//    public void ConsumirApiET() {
//        String url = "http://192.168.1.111/ApisMovil/api.php?op=eliminarT&ced=" + Cedula;
//        OkHttpClient cliente = new OkHttpClient();
//
//        Request get = new Request.Builder().url(url).build();
//        cliente.newCall(get).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ConsultaTurnos_Activity.this, "Fallo la conexión", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    ResponseBody responseBody = response.body();
//                    if (response.isSuccessful()) {
//                        respuesta = responseBody.string();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (respuesta.equals("1")) {
//                                    Toast.makeText(ConsultaTurnos_Activity.this, "El turno ha sido cancelado", Toast.LENGTH_SHORT).show();
//                                } else if (respuesta.equals("2")) {
//                                    Toast.makeText(ConsultaTurnos_Activity.this, "Error al cancelar el turno", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    } else {
//                        throw new IOException("Respuesta inesperada" + response);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//}
