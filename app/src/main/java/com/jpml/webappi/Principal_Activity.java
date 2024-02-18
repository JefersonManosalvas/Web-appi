package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.VideoView;

import androidx.annotation.NonNull;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Principal_Activity extends AppCompatActivity {
    private Toolbar toolbar1;

    String respuesta, Usuario, Clave;
    String cedula ,nombres,telefono;

    VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

       


        Bundle datos = this.getIntent().getExtras();
         Usuario = datos.getString("usuario");
         Clave = datos.getString("clave");
        ConsultarAPI();


    }


    public void ConsultarAPI() {
        String url = "http://192.168.1.111/ApisMovil/api.php?op=ConsultaDatos&usu="+Usuario+"&cla="+Clave;
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
                        Principal_Activity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (respuesta.equals("[]")) {
                                        Toast.makeText(Principal_Activity.this, "error", Toast.LENGTH_SHORT).show();

                                    } else {
                                        JSONObject json = new JSONObject(respuesta);

                                        cedula = json.getString("Cedula");
                                        nombres = json.getString("nombre_completo");
                                        telefono = json.getString("telefono");
                                        guardarEnSharedPreferences();
                                    }
                                    } catch(JSONException e){
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

    private void guardarEnSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cedula", cedula);
        editor.putString("nombre", nombres);
        editor.putString("telefono", telefono);
        editor.apply();
    }


    public  void agregarT(View view){

        Intent intent = new Intent(Principal_Activity.this, RegistraTurno_Activity.class);
        startActivity(intent);

    }

    public  void consultaT(View view){
//        Bundle datos = new Bundle();
//        String Cedula = cedula;
        Intent intent = new Intent(Principal_Activity.this, ConsultaTurnos_Activity.class);
//        datos.putString("cedula", Cedula);
//        intent.putExtras(datos);
        startActivity(intent);

    }





}



