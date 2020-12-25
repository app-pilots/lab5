package com.grupo2.grupo2_ta3_iniciosesionfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

public class registros extends AppCompatActivity {
    DatabaseReference db_reference;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        context=getApplicationContext();
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupo2");

        leerRegistros();
    }

    public void leerRegistros(){

        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mostrarRegistrosPorPantalla(snapshot);
                }g
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Error",error.toException().toString());
            }
        });
    }

    public void mostrarRegistrosPorPantalla(DataSnapshot snapshot){

        LinearLayout contTemp = (LinearLayout) findViewById(R.id.ContenedorTemp);
        LinearLayout contHum = (LinearLayout) findViewById(R.id.ContenedorHum);
        String tempVal = String.valueOf(snapshot.child("payload_fields").child("temperatura").getValue());
        String humVal = String.valueOf(snapshot.child("payload_fields").child("humedad").getValue());
        TextView temp = new TextView(getApplicationContext());
        temp.setText(tempVal+" °C");
        contTemp.addView(temp);
        TextView hum = new TextView(getApplicationContext());
        hum.setText(humVal+" %");
        contHum.addView(hum);
    }






}