package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RecUsuario extends AppCompatActivity {

    String respuesta;
    EditText cedula, usuario,Nclave;
    Button recuperar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_usuario);
        cedula=findViewById(R.id.edCedulaR);
        usuario=findViewById(R.id.edUsuarioR);
        Nclave=findViewById(R.id.edNuevaContra);
        recuperar=findViewById(R.id.btnRecupera);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camposValidos()) {
                    ConsumirApi();
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


            public void ConsumirApi() {
                String url = "http://192.168.1.106/ApisMovil/api.php?op=Modifica&ced="+cedula.getText()+"&usu="+usuario.getText()+"&Ncla="+Nclave.getText();


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
                                RecUsuario.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (respuesta.equals("1")) {
                                            Toast.makeText(RecUsuario.this, "La contrasenia se modifico correctamente", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RecUsuario.this, MainActivity.class);
                                            startActivity(intent);

                                        } else if (respuesta.equals("2")) {
                                            Toast.makeText(RecUsuario.this, "No se pudo modificar la contraseña", Toast.LENGTH_SHORT).show();
                                        }
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
        private boolean camposValidos() {
            return !cedula.getText().toString().isEmpty() &&
                    !usuario.getText().toString().isEmpty() &&
                    !Nclave.getText().toString().isEmpty();
        }


    }