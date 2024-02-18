package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    EditText v1,v2;
    TextView creaUsu,recContra;

    Button btn1;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        v1=findViewById(R.id.edUsuario);
        v2=findViewById(R.id.edClave);
        btn1=findViewById(R.id.btnAceptar);
        creaUsu=findViewById(R.id.txtCrearCuenta);
        recContra=findViewById(R.id.txtOlvidoContrasena);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirApi();
            }
        });

    }

    public void ConsumirApi(){
        String url="http://192.168.1.111/ApisMovil/api.php?op=validar&usu="+v1.getText()+"&cla="+v2.getText();


        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
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
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (respuesta.equals("2")) {
                                    //Toast.makeText(MainActivity.this, "Usuario correcto", Toast.LENGTH_SHORT).show();
                                    Bundle datos = new Bundle();
                                    String Usuario = v1.getText().toString();
                                    String Clave = v2.getText().toString();
                                    Intent intent = new Intent(MainActivity.this, Principal_Activity.class);
                                    datos.putString("usuario", Usuario);
                                    datos.putString("clave", Clave);
                                    intent.putExtras(datos);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
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



    public  void crearCuenta(View view){
        Intent intent = new Intent(MainActivity.this, CrearUsuario.class);
        startActivity(intent);

    }


    public  void recuperarContrasena(View view){
        Intent intent = new Intent(MainActivity.this, RecUsuario.class);
        startActivity(intent);

    }





}