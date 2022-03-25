package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.R;

public class AddPlantActivity extends AppCompatActivity {
    private Button addBtn, cancelBtn;

    private EditText nameTxt, scifiNameTxt, uriTxt, descriptionTxt;

    private AutoCompleteTextView stageTxt, seedWaterRateTxt, seedlingWaterRateTxt, matureWaterRateTxt,
            minSeedMoistureTxt, maxSeedMoistureTxt, minSeedlingMoistureTxt, maxSeedlingMoistureTxt,
            minMatureMoistureTxt, maxMatureMoistureTxt;

    private ArrayAdapter<String> adapterItems;

    private final String[] stages = {"Seed", "Seedling", "Mature"};
    private final String[] seedWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] seedlingWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] matureWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] minSeedMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxSeedMoistures = {"20", "40", "60", "80", "100"};
    private final String[] minSeedlingMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxSeedlingMoistures = {"20", "40", "60", "80", "100"};
    private final String[] minMatureMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxMatureMoistures = {"20", "40", "60", "80", "100"};

    private PlantInfo plantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Plant");

        // Check if plant info is being passed in
        Intent intent = new Intent();
        plantInfo = intent.getParcelableExtra("PLANTINFO");


        nameTxt = findViewById(R.id.editPlantName);
        scifiNameTxt = findViewById(R.id.editScifiName);
        uriTxt = findViewById(R.id.editUri);
        descriptionTxt = findViewById(R.id.editDescription);

        // setting stage items
        stageTxt = findViewById(R.id.stage_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, stages);
        stageTxt.setAdapter(adapterItems);

        // setting seed water rate items
        seedWaterRateTxt = findViewById(R.id.seed_water_rate_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, seedWaterRates);
        seedWaterRateTxt.setAdapter(adapterItems);

        // seedling water rate items...
        seedlingWaterRateTxt = findViewById(R.id.seedling_water_rate_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, seedlingWaterRates);
        seedlingWaterRateTxt.setAdapter(adapterItems);

        matureWaterRateTxt = findViewById(R.id.mature_water_rate_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, matureWaterRates);
        matureWaterRateTxt.setAdapter(adapterItems);

        minSeedMoistureTxt = findViewById(R.id.min_seed_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minSeedMoistures);
        minSeedMoistureTxt.setAdapter(adapterItems);

        maxSeedMoistureTxt = findViewById(R.id.max_seed_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxSeedMoistures);
        maxSeedMoistureTxt.setAdapter(adapterItems);

        minSeedlingMoistureTxt = findViewById(R.id.min_seedling_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minSeedlingMoistures);
        minSeedlingMoistureTxt.setAdapter(adapterItems);

        maxSeedlingMoistureTxt = findViewById(R.id.max_seedling_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxSeedlingMoistures);
        maxSeedlingMoistureTxt.setAdapter(adapterItems);

        minMatureMoistureTxt = findViewById(R.id.min_mature_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minMatureMoistures);
        minMatureMoistureTxt.setAdapter(adapterItems);

        maxMatureMoistureTxt = findViewById(R.id.max_mature_moisture_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxMatureMoistures);
        maxMatureMoistureTxt.setAdapter(adapterItems);

        // add btn click listener
        addBtn = findViewById(R.id.add_plant_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, scifiName, uri, description, stage;
                float seedWaterRate, seedlingWaterRate, matureWaterRate, minSeedMoisture, maxSeedMoisture,
                        minSeedlingMoisture, maxSeedlingMoisture, minMatureMoisture, maxMatureMoisture;

                name = nameTxt.getText().toString();
                scifiName = scifiNameTxt.getText().toString();
                uri = uriTxt.getText().toString();
                description = descriptionTxt.getText().toString();
                stage = stageTxt.getText().toString();

                try {
                    seedWaterRate = Float.parseFloat(seedWaterRateTxt.getText().toString());
                    seedlingWaterRate = Float.parseFloat(seedlingWaterRateTxt.getText().toString());
                    matureWaterRate = Float.parseFloat(matureWaterRateTxt.getText().toString());
                    minSeedMoisture = Float.parseFloat(minSeedMoistureTxt.getText().toString());
                    maxSeedMoisture = Float.parseFloat(maxSeedMoistureTxt.getText().toString());
                    minSeedlingMoisture = Float.parseFloat(minSeedlingMoistureTxt.getText().toString());
                    maxSeedlingMoisture = Float.parseFloat(maxSeedlingMoistureTxt.getText().toString());
                    minMatureMoisture = Float.parseFloat(minMatureMoistureTxt.getText().toString());
                    maxMatureMoisture = Float.parseFloat(maxMatureMoistureTxt.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(AddPlantActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validating user input
                if (minSeedMoisture > maxSeedMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Seed Moisture can not be greater than Max Seed Moisture", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (minSeedlingMoisture > maxSeedlingMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Seedling Moisture can not be greater than Max Seedling Moisture", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (minMatureMoisture > maxMatureMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Mature-Plant Moisture can not be greater than Max Mature-Plant Moisture", Toast.LENGTH_SHORT).show();
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

        // cancel btn click listener
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