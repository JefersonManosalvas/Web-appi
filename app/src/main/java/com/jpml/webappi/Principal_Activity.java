package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import android.app.DatePickerDialog;
import android.content.Intent;
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

    String respuesta,DatosResult,usuario,clave;

    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String clave = intent.getStringExtra("clave");
        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        video=findViewById(R.id.videoView_v);

        video.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.turnos));
        video.start();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.inicio) {
                        Intent reg = new Intent(Principal_Activity.this, Principal_Activity.class);
                        startActivity(reg);


                    } else if (item.getItemId() == R.id.agrega) {
                        Intent reg = new Intent(Principal_Activity.this, RegistraTurno_Activity.class);
                        // Pasar los valores como extras al Intent
                        reg.putExtra("usuario", usuario);
                        reg.putExtra("clave", clave);
                        startActivity(reg);
                    } else if (item.getItemId() == R.id.consultar) {
                        Intent intent = new Intent(Principal_Activity.this, ConsultaTurnos_Activity.class);
                        // Pasar los valores como extras al Intent
                        intent.putExtra("Usu", usuario);
                        intent.putExtra("Clave", clave);
                        startActivity(intent);
                    }
                    return false;
                }
            });


        }

    }
