package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.R;
import com.example.plantreapp.connection.ConnBtnActivity;
import com.example.plantreapp.connection.ConnectionActivity;
import com.example.plantreapp.journals.JournalsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlantInfoActivity extends AppCompatActivity {

    private TextView plantInfoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        Intent i = getIntent();
        PlantInfo info = i.getParcelableExtra("plantInfo");

        plantInfoTxt = findViewById(R.id.plantInfoTextView);
        plantInfoTxt.setText(info.getName());
        plantInfoTxt.append("\r\nScientific Name: "+ info.getScifiName() + "\r\n");
        //plantInfoTxt.append(info.getUri() + "\r\n");
        plantInfoTxt.append("Description: " + info.getDescription() + "\r\n");
        plantInfoTxt.append("Stage: " + info.getStage() + "\r\n");
        plantInfoTxt.append("Seed Water Rate: " + info.getSeedWaterRate() + "\r\n");
        plantInfoTxt.append("Seedling Water Rate: " + info.getSeedlingWaterRate() + "\r\n");
        plantInfoTxt.append("Mature Water Rate: " + info.getMatureWaterRate() + "\r\n");
        plantInfoTxt.append("Min Seed Moisture: " + info.getMinSeedMoisture() + "%\r\n");
        plantInfoTxt.append("Max Seed Moisture: " + info.getMaxSeedMoisture() + "%\r\n");
        plantInfoTxt.append("Min Seedling Moisture: " + info.getMinSeedlingMoisture() + "%\r\n");
        plantInfoTxt.append("Max Seedling Moisture: " + info.getMaxSeedlingMoisture() + "%\r\n");
        plantInfoTxt.append("Min Mature Moisture: " + info.getMinMatureMoisture() + "%\r\n");
        plantInfoTxt.append("Max Mature Moisture: " + info.getMaxMatureMoisture() + "%\r\n");

        // set actionbar title to "my plants"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(info.getName());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.my_plants_item);

        // nav click handler
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext(), ConnBtnActivity.class));
                        return true;
                    case R.id.my_plants_item:
                        startActivity(new Intent(getApplicationContext(), MyPlantsActivity.class));
                        return true;
                    case R.id.search_item:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        return true;
                    case R.id.connection_item:
                        startActivity(new Intent(getApplicationContext(), ConnectionActivity.class));
                        return true;
                }
                return false;
            }
        });
    }

    public void launchJournals(View view) {
        Intent i = getIntent();
        PlantInfo info = i.getParcelableExtra("plantInfo");
        int uid = i.getIntExtra("plantUid", 0);

        Intent intent = new Intent(PlantInfoActivity.this, JournalsActivity.class);
        intent.putExtra("plantName", info.getName());
        intent.putExtra("plantUid", uid);
        startActivity(intent);
    }
}