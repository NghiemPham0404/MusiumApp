package com.example.musiumproject.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musiumproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationBar);
        navigationView.setItemIconTintList(null);
    }
}