package com.mobile.simulado_estacionamento.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile.simulado_estacionamento.MainActivity;
import com.mobile.simulado_estacionamento.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences("usuario", MODE_PRIVATE);
        prefs.edit().putBoolean("isAdmin", false).apply();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class); // sempre abre tela comum
            startActivity(intent);
            finish();
        }, 2000);
    }


    private void abrirMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
