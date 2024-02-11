package com.jpml.webappi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConsultaTurnos_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_turnos);


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
}