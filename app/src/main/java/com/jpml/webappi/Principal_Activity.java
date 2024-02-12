package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import android.app.DatePickerDialog;
import android.content.Intent;
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
//    TextView fechas;
//
//    EditText cedula ,nombre,telefono;
//
//    Button calendario;
    String respuesta,DatosResult;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        toolbar1=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);


        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String clave = intent.getStringExtra("clave");






        BottomNavigationView bottomNavigationView = findViewById(R.id.Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.inicio) {
                    Toast.makeText(Principal_Activity.this,"A SELECCIONADO LA OPCION 1",Toast.LENGTH_LONG).show();

                } else if (item.getItemId()==R.id.agrega) {
                    Intent reg = new Intent(Principal_Activity.this, RegistraTurno_Activity.class);
                    // Pasar los valores como extras al Intent
                    reg.putExtra("usuario", usuario);
                    reg.putExtra("clave", clave);
                    startActivity(reg);
                } else if (item.getItemId()==R.id.consultar) {
                    Intent intent = new Intent(Principal_Activity.this, ConsultaTurnos_Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });






    }


//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//        return true;
//    }
//
//
//    public void AbrirCalendario(View view){
//        Calendar cal= Calendar.getInstance();
//        int anio= cal.get(Calendar.YEAR);
//        int mes= cal.get(Calendar.MONTH);
//        int dia= cal.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog dpd=new DatePickerDialog(Principal_Activity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                String fecha= dayOfMonth+"/"+(month+1)+"/"+year;
//                fechas.setText(fecha);
//            }
//        },dia,mes,anio);
//        dpd.show();
//}



//
//
//
//    private void showInputDialog(final String operation) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(operation);
//
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.reserva, null);
//        builder.setView(dialogView);
//
//        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//            }
//        });
//
//        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        builder.show();
//    }


//


    }
