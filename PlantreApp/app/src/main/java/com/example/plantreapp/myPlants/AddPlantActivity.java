package com.example.plantreapp.myPlants;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plantreapp.R;
import com.example.plantreapp.TimerService;
import com.example.plantreapp.logs.AddLogActivity;
import com.example.plantreapp.logs.LogsActivity;

public class AddPlantActivity extends AppCompatActivity {
    private Button addBtn, cancelBtn;

    private EditText nameTxt, scifiNameTxt, uriTxt, descriptionTxt, stageTxt ,seedWaterRateTxt, seedlingWaterRateTxt,
            matureWaterRateTxt, minSeedMoistureTxt, maxSeedMoistureTxt, minSeedlingMoistureTxt, maxSeedlingMoistureTxt,
            minMatureMoistureTxt, maxMatureMoistureTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Plant");

        nameTxt = findViewById(R.id.editPlantName);
        scifiNameTxt = findViewById(R.id.editScifiName);
        uriTxt = findViewById(R.id.editUri);
        descriptionTxt = findViewById(R.id.editDescription);
        stageTxt = findViewById(R.id.editStage);
        seedWaterRateTxt = findViewById(R.id.editSeedWaterRate);
        seedlingWaterRateTxt = findViewById(R.id.editSeedlingWaterRate);
        matureWaterRateTxt = findViewById(R.id.editMatureWaterRate);
        minSeedMoistureTxt = findViewById(R.id.editMinSeedMoisture);
        maxSeedMoistureTxt = findViewById(R.id.editMaxSeedMoisture);
        minSeedlingMoistureTxt = findViewById(R.id.editMinSeedlingMoisture);
        maxSeedlingMoistureTxt = findViewById(R.id.editMaxSeedlingMoisture);
        minMatureMoistureTxt = findViewById(R.id.editMinMatureMoisture);
        maxMatureMoistureTxt = findViewById(R.id.editMaxMatureMoisture);

        addBtn = findViewById(R.id.add_plant_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, scifiName, uri, description, stage;
                int seedWaterRate, seedlingWaterRate, matureWaterRate, minSeedMoisture, maxSeedMoisture,
                        minSeedlingMoisture, maxSeedlingMoisture, minMatureMoisture, maxMatureMoisture;

                name = nameTxt.getText().toString();
                scifiName = scifiNameTxt.getText().toString();
                uri = uriTxt.getText().toString();
                description = descriptionTxt.getText().toString();
                stage = stageTxt.getText().toString();
                // todo: validate user input for integers (null or negative)
                try {
                    seedWaterRate = Integer.parseInt(seedWaterRateTxt.getText().toString());
                    seedlingWaterRate = Integer.parseInt(seedlingWaterRateTxt.getText().toString());
                    matureWaterRate = Integer.parseInt(matureWaterRateTxt.getText().toString());
                    minSeedMoisture = Integer.parseInt(minSeedMoistureTxt.getText().toString());
                    maxSeedMoisture = Integer.parseInt(maxSeedMoistureTxt.getText().toString());
                    minSeedlingMoisture = Integer.parseInt(minSeedlingMoistureTxt.getText().toString());
                    maxSeedlingMoisture = Integer.parseInt(maxSeedlingMoistureTxt.getText().toString());
                    minMatureMoisture = Integer.parseInt(minMatureMoistureTxt.getText().toString());
                    maxMatureMoisture = Integer.parseInt(maxMatureMoistureTxt.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(AddPlantActivity.this, String.valueOf("Fields Must Not be Blank"), Toast.LENGTH_SHORT).show();
                    return;
                }

                PlantInfo plantInfo = new PlantInfo(name, scifiName, uri, description, stage,seedWaterRate,
                        seedlingWaterRate, matureWaterRate, minSeedMoisture, maxSeedMoisture, minSeedlingMoisture,
                        maxSeedlingMoisture, minMatureMoisture, maxMatureMoisture);

                Intent intent = new Intent(AddPlantActivity.this, MyPlantsActivity.class);
                intent.putExtra("plantInfo", plantInfo);
                startActivity(intent);
            }
        });

        cancelBtn = findViewById(R.id.add_plant_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlantActivity.this, MyPlantsActivity.class);
                startActivity(intent);
            }
        });
    }
}

/*
        name
        scifiName
        uri
        description
        stage
        seedWaterRate
        seedlingWaterRate
        matureWaterRate
        minSeedMoisture
        maxSeedMoisture
        minSeedlingMoisture
        maxSeedlingMoisture
        minMatureMoisture
        maxMatureMoisture
* */