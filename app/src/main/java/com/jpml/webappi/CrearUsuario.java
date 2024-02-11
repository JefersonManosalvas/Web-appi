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



public class CrearUsuario extends AppCompatActivity {

    EditText cedula,usu,correo, cont,nombres,telefono;

    String respuesta;
    Button guarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);
        cedula=findViewById(R.id.edCedula);
        usu=findViewById(R.id.edNombUsuario);
        correo=findViewById(R.id.edEmail);
        cont=findViewById(R.id.edContrasenia);
        nombres=findViewById(R.id.edNombres);
        telefono=findViewById(R.id.edTelefono);
        guarda=findViewById(R.id.btnRegistrar);
        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirApi();
            }
        });

    }





    public void ConsumirApi(){
        String datos=nombres.getText().toString();
        String[] parts = datos.split(" ");
        String nombre = parts[0];
        String apellido = parts[1];

        //String url="https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a="+v1.getText()+"&b="+v2.getText();
        String url="http://192.168.1.108/ApisMovil/api.php?op=insertar&ced="+cedula.getText()+"&nomb="+nombre+"&ape="+apellido+"&tele="+telefono.getText()+"&correo="+correo.getText()+"&usu="+usu.getText()+"&cla="+cont.getText();


        OkHttpClient registra=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


        registra.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{

                    ResponseBody responseBody=response.body();
                    if( response.isSuccessful()){

                        respuesta = responseBody.string();
                        CrearUsuario.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //  res1.setText(respuesta);
                                //Toast.makeText(CrearUsuario.this, respuesta, Toast.LENGTH_SHORT).show();

                                if (respuesta.equals("1")) {
                                    Toast.makeText(CrearUsuario.this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CrearUsuario.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CrearUsuario.this, "ERROR AL CREAR EL USUARIO INTENTELO MAS TARDE", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        throw new IOException("Respuesta inesperada"+response);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}