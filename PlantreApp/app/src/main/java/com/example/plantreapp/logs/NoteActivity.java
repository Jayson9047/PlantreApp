package com.example.plantreapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.R;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.example.plantreapp.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NoteActivity extends AppCompatActivity {

    //TextView logName, logInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LOG");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home_item);

        // nav click handler
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext(), MyPlantsActivity.class));
                        return true;
                    case R.id.search_item:
                        //startActivity(new Intent(getApplicationContext(), Search.class));
                        return true;
                    case R.id.settings_item:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        return true;
                }
                return false;
            }
        });


        //Intent i = getIntent();
        //String id = i.getStringExtra("logID");
    }
}