package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.plantreapp.R;

public class AddPlantActivity extends AppCompatActivity {
    private Button addBtn, cancelBtn;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    ConstraintLayout moistureLayout, hourLayout;
    SeekBar seekbar1, seekbar2, seekbar3, seekbar4, seekbar5, seekbar6, seekbar7, seekbar8, seekbar9;
    TextView Seed_Max, Seed_min, Seeding_max, Seeding_min, Mature_max, Mature_min, Seed_hour, Seeding_hour, Mature_hour;

    private EditText nameTxt, scifiNameTxt, uriTxt, descriptionTxt;

    private AutoCompleteTextView stageTxt;
    private float maxSeedMoisture, minSeedMoisture,maxSeedingMoisture, minSeedingMoisture, maxMatureMoisture, minMatureMoisture , hourRateSeed, hourRateSeeding, hourRateMature;

    private ArrayAdapter<String> adapterItems;
    private int pos;

    private final String[] stages = {"Seed", "Seedling", "Mature"};

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

         //setting stage items
        stageTxt = findViewById(R.id.stage_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, stages);
        stageTxt.setAdapter(adapterItems);

        radioGroup=(RadioGroup)findViewById(R.id.radio);

        seekbar1=(SeekBar)findViewById(R.id.seekBar);
        seekbar2=(SeekBar)findViewById(R.id.seekBar2);
        seekbar3=(SeekBar)findViewById(R.id.seekBar3);
        seekbar4=(SeekBar)findViewById(R.id.seekBar4);
        seekbar5=(SeekBar)findViewById(R.id.seekBar5);
        seekbar6=(SeekBar)findViewById(R.id.seekBar6);
        seekbar7=(SeekBar)findViewById(R.id.seekBar7);
        seekbar8=(SeekBar)findViewById(R.id.seekBar8);
        seekbar9=(SeekBar)findViewById(R.id.seekBar9);

        Seed_Max =(TextView)findViewById(R.id.max_mositure);
        Seed_min =(TextView)findViewById(R.id.min_mositure);
        Seeding_max=(TextView)findViewById(R.id.Seeding_max_mositure);
        Seeding_min = (TextView)findViewById(R.id.Seeding_min_mositure);
        Mature_max = (TextView)findViewById(R.id.Mature_max_mositure);
        Mature_min = (TextView)findViewById(R.id.Mature_min_mositure);
        Seed_hour =(TextView)findViewById(R.id.Seed_hour);
        Seeding_hour =(TextView)findViewById(R.id.Seeding_hour);
        Mature_hour =(TextView)findViewById(R.id.Mature_hour);
        radioButton1 = findViewById(R.id.radio_one);
        radioButton2 = findViewById(R.id.radio_two);

        moistureLayout = findViewById(R.id.moistureLayout);
        hourLayout = findViewById(R.id.hourLayout);
        hourLayout.setVisibility(View.INVISIBLE);

        Seed_Max.setVisibility(View.INVISIBLE);
        Seed_min.setVisibility(View.INVISIBLE);
        Seeding_max.setVisibility(View.INVISIBLE);
        Seeding_min.setVisibility(View.INVISIBLE);
        Mature_max.setVisibility(View.INVISIBLE);
        Mature_min.setVisibility(View.INVISIBLE);

        seekbar1.setVisibility(View.INVISIBLE);
        seekbar2.setVisibility(View.INVISIBLE);
        seekbar3.setVisibility(View.INVISIBLE);
        seekbar4.setVisibility(View.INVISIBLE);
        seekbar5.setVisibility(View.INVISIBLE);
        seekbar6.setVisibility(View.INVISIBLE);

        Seed_hour.setVisibility(View.INVISIBLE);
        Seeding_hour.setVisibility(View.INVISIBLE);
        Mature_hour.setVisibility(View.INVISIBLE);
        seekbar7.setVisibility(View.INVISIBLE);
        seekbar8.setVisibility(View.INVISIBLE);
        seekbar9.setVisibility(View.INVISIBLE);


        stageTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(radioButton1.isChecked()) {
                    if (i == 0) {
                        Seed_Max.setVisibility(View.VISIBLE);
                        Seed_min.setVisibility(View.VISIBLE);
                        Seeding_max.setVisibility(View.INVISIBLE);
                        Seeding_min.setVisibility(View.INVISIBLE);
                        Mature_max.setVisibility(View.INVISIBLE);
                        Mature_min.setVisibility(View.INVISIBLE);

                        seekbar1.setVisibility(View.VISIBLE);
                        seekbar2.setVisibility(View.VISIBLE);
                        seekbar3.setVisibility(View.INVISIBLE);
                        seekbar4.setVisibility(View.INVISIBLE);
                        seekbar5.setVisibility(View.INVISIBLE);
                        seekbar6.setVisibility(View.INVISIBLE);
                    }
                    if (i == 1) {
                        Seed_Max.setVisibility(View.INVISIBLE);
                        Seed_min.setVisibility(View.INVISIBLE);
                        Seeding_max.setVisibility(View.VISIBLE);
                        Seeding_min.setVisibility(View.VISIBLE);
                        Mature_max.setVisibility(View.INVISIBLE);
                        Mature_min.setVisibility(View.INVISIBLE);

                        seekbar1.setVisibility(View.INVISIBLE);
                        seekbar2.setVisibility(View.INVISIBLE);
                        seekbar3.setVisibility(View.VISIBLE);
                        seekbar4.setVisibility(View.VISIBLE);
                        seekbar5.setVisibility(View.INVISIBLE);
                        seekbar6.setVisibility(View.INVISIBLE);
                    }
                    if (i == 2) {
                        Seed_Max.setVisibility(View.INVISIBLE);
                        Seed_min.setVisibility(View.INVISIBLE);
                        Seeding_max.setVisibility(View.INVISIBLE);
                        Seeding_min.setVisibility(View.INVISIBLE);
                        Mature_max.setVisibility(View.VISIBLE);
                        Mature_min.setVisibility(View.VISIBLE);

                        seekbar1.setVisibility(View.INVISIBLE);
                        seekbar2.setVisibility(View.INVISIBLE);
                        seekbar3.setVisibility(View.INVISIBLE);
                        seekbar4.setVisibility(View.INVISIBLE);
                        seekbar5.setVisibility(View.VISIBLE);
                        seekbar6.setVisibility(View.VISIBLE);
                    }
                }
                 if (radioButton2.isChecked())
                {
                    if(i==0)
                    {
                        Seed_hour.setVisibility(View.VISIBLE);
                        Seeding_hour.setVisibility(View.INVISIBLE);
                        Mature_hour.setVisibility(View.INVISIBLE);
                        seekbar7.setVisibility(View.VISIBLE);
                        seekbar8.setVisibility(View.INVISIBLE);
                        seekbar9.setVisibility(View.INVISIBLE);
                    }
                    if(i==1)
                    {
                        Seed_hour.setVisibility(View.INVISIBLE);
                        Seeding_hour.setVisibility(View.VISIBLE);
                        Mature_hour.setVisibility(View.INVISIBLE);
                        seekbar7.setVisibility(View.INVISIBLE);
                        seekbar8.setVisibility(View.VISIBLE);
                        seekbar9.setVisibility(View.INVISIBLE);
                    }
                    if(i==2)
                    {
                        Seed_hour.setVisibility(View.INVISIBLE);
                        Seeding_hour.setVisibility(View.INVISIBLE);
                        Mature_hour.setVisibility(View.VISIBLE);
                        seekbar7.setVisibility(View.INVISIBLE);
                        seekbar8.setVisibility(View.INVISIBLE);
                        seekbar9.setVisibility(View.VISIBLE);
                    }
                }


            }
        });
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seed_Max.setText("Seed Max Soil Moisture : " + String.valueOf(progress));
                maxSeedMoisture = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seed_min.setText("Seed Min Soil Moisture : " + String.valueOf(progress));
                minSeedMoisture= progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seeding_max.setText("Seeding Max Soil Moisture : " + String.valueOf(progress));
                maxSeedingMoisture= progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seeding_min.setText("Seeding Max Soil Moisture : " + String.valueOf(progress));
                minSeedingMoisture=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Mature_max.setText("Mature Max Soil Moisture : " + String.valueOf(progress));
                maxMatureMoisture= progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Mature_min.setText("Mature Min Soil Moisture : " + String.valueOf(progress));
                minMatureMoisture=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seed_hour.setText("Seed Water Rate (/hr) : " + String.valueOf(progress));
                hourRateSeed=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seeding_hour.setText("Seeding Water Rate (/hr) : " + String.valueOf(progress));
                hourRateSeeding=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar9.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Mature_hour.setText("Mature Water Rate (/hr) : " + String.valueOf(progress));
                hourRateMature=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // add btn click listener
        addBtn = findViewById(R.id.add_plant_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, scifiName, uri, description, stage;

                name = nameTxt.getText().toString();
                scifiName = scifiNameTxt.getText().toString();
                uri = uriTxt.getText().toString();
                description = descriptionTxt.getText().toString();
                stage = stageTxt.getText().toString();

                if (name.length()==0 || scifiName.length()==0 || uri.length()==0 || description.length()==0 || stage.length()==0 )
                {
                    Toast.makeText(AddPlantActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                } catch (Exception e) {
                    Toast.makeText(AddPlantActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    return;
                }

                // validating user input
                if (minSeedMoisture > maxSeedMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Seed Moisture can not be greater than Max Seed Moisture", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (minSeedingMoisture > maxSeedingMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Seedling Moisture can not be greater than Max Seedling Moisture", Toast.LENGTH_SHORT).show();

                    return;
                }
                else if (minMatureMoisture > maxMatureMoisture) {
                    Toast.makeText(AddPlantActivity.this, "Min Mature-Plant Moisture can not be greater than Max Mature-Plant Moisture", Toast.LENGTH_SHORT).show();

                    return;
                }

                PlantInfo plantInfo = new PlantInfo(name, scifiName, uri, description, stage,hourRateSeed,
                        hourRateSeeding, hourRateMature, minSeedMoisture, maxSeedMoisture, minSeedingMoisture,
                        maxSeedingMoisture, minMatureMoisture, maxMatureMoisture);

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
    public void checkbutton(View v){
        int radioId= radioGroup.getCheckedRadioButtonId();



        if (radioButton1.isChecked()) {
            moistureLayout.setVisibility(View.VISIBLE);
            hourLayout.setVisibility(View.INVISIBLE);
           // Toast.makeText(this,"Seed Max Moisture: "+ maxSeedMoisture, Toast.LENGTH_SHORT).show();
        } else if (radioButton2.isChecked()) {
            moistureLayout.setVisibility(View.INVISIBLE);
            hourLayout.setVisibility(View.VISIBLE);
        }

    }


}