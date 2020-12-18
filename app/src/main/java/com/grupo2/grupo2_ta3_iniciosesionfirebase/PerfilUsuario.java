package com.grupo2.grupo2_ta3_iniciosesionfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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


public class PerfilUsuario extends AppCompatActivity {
    TextView txt_id, txt_name, txt_email, txt_phone;
    ImageView imv_photo;
    Button btn_logout;
    DatabaseReference db_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent intent = getIntent();
        HashMap<String, String> info_user = (HashMap<String, String>) intent.getSerializableExtra("info_user");

        txt_id = findViewById(R.id.txt_userId);
        txt_name = findViewById(R.id.txt_nombre);
        txt_email = findViewById(R.id.txt_correo);
        imv_photo = findViewById(R.id.imv_photo);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_id.setText(info_user.get("user_id"));
        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        txt_phone.setText(info_user.get("user_phone"));
        //info_user.get("user_")
        if (info_user.get("user_phone").equals("")) {
            txt_phone.setVisibility(View.GONE);
        }
        String photo = info_user.get("user_photo");
        Picasso.with(getApplicationContext()).load(photo).into(imv_photo);

        //aqui inciar base de datos
        iniciarBaseDeDatos();
        leerTweets();
        escribirTweets(info_user.get("user_name"));

    }

    public void cerrarSesion(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg", "cerrarSesion");
        startActivity(intent);
    }
    public void iniciarBaseDeDatos(){
        db_reference=FirebaseDatabase.getInstance().getReference().child("Grupo");
    }
    public void leerTweets(){
        db_reference.child("Grupo 2").child("tweets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            System.out.println(snapshot);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.toException());
                    }
                });
            }
    public void escribirTweets(String autor){
        String tweet="hola mundo firebase 2";
        String fecha="11/23/2020";
        Map<String,String> hola_tweet= new HashMap<String, String>();
        hola_tweet.put("autor",autor);
        hola_tweet.put("fecha",fecha);
        DatabaseReference tweets = db_reference.child("Grupo 2").child("Tweets");
        tweets.setValue(tweet);
        tweets.child(tweet).child("autor").setValue(autor);
        tweets.child(tweet).child("fecha").setValue(fecha);
    }


}