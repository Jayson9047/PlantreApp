package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

import com.example.plantreapp.R;

public class AddPlantActivity extends AppCompatActivity {
    private Button addBtn, cancelBtn;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    SeekBar seekbar1, seekbar2, seekbar3, seekbar4, seekbar5, seekbar6, seekbar7, seekbar8, seekbar9;
    TextView Seed_Max, Seed_min, Seeding_max, Seeding_min, Mature_max, Mature_min, Seed_hour, Seeding_hour, Mature_hour;

    private EditText nameTxt, scifiNameTxt, uriTxt, descriptionTxt;

    private AutoCompleteTextView stageTxt, seedWaterRateTxt, seedlingWaterRateTxt, matureWaterRateTxt,
            minSeedMoistureTxt, maxSeedMoistureTxt, minSeedlingMoistureTxt, maxSeedlingMoistureTxt,
            minMatureMoistureTxt, maxMatureMoistureTxt;

    private ArrayAdapter<String> adapterItems;

    private final String[] stages = {"Seed", "Seedling", "Mature"};
  /*  private final String[] seedWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] seedlingWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] matureWaterRates = {"1", "2", "3", "4", "5"};
    private final String[] minSeedMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxSeedMoistures = {"20", "40", "60", "80", "100"};
    private final String[] minSeedlingMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxSeedlingMoistures = {"20", "40", "60", "80", "100"};
    private final String[] minMatureMoistures = {"20", "40", "60", "80", "100"};
    private final String[] maxMatureMoistures = {"20", "40", "60", "80", "100"};*/

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



        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Seed_Max.setText("Seed Max Soil Moisture : " + String.valueOf(progress));
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


//        // setting seed water rate items
//        seedWaterRateTxt = findViewById(R.id.seed_water_rate_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, seedWaterRates);
//        seedWaterRateTxt.setAdapter(adapterItems);
//
//        // seedling water rate items...
//        seedlingWaterRateTxt = findViewById(R.id.seedling_water_rate_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, seedlingWaterRates);
//        seedlingWaterRateTxt.setAdapter(adapterItems);
//
//        matureWaterRateTxt = findViewById(R.id.mature_water_rate_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, matureWaterRates);
//        matureWaterRateTxt.setAdapter(adapterItems);
//
//        minSeedMoistureTxt = findViewById(R.id.min_seed_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minSeedMoistures);
//        minSeedMoistureTxt.setAdapter(adapterItems);
//
//        maxSeedMoistureTxt = findViewById(R.id.max_seed_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxSeedMoistures);
//        maxSeedMoistureTxt.setAdapter(adapterItems);
//
//        minSeedlingMoistureTxt = findViewById(R.id.min_seedling_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minSeedlingMoistures);
//        minSeedlingMoistureTxt.setAdapter(adapterItems);
//
//        maxSeedlingMoistureTxt = findViewById(R.id.max_seedling_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxSeedlingMoistures);
//        maxSeedlingMoistureTxt.setAdapter(adapterItems);
//
//        minMatureMoistureTxt = findViewById(R.id.min_mature_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, minMatureMoistures);
//        minMatureMoistureTxt.setAdapter(adapterItems);
//
//        maxMatureMoistureTxt = findViewById(R.id.max_mature_moisture_txt);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, maxMatureMoistures);
//        maxMatureMoistureTxt.setAdapter(adapterItems);

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
    public void checkbutton(View v){
        int radioId= radioGroup.getCheckedRadioButtonId();
        radioButton1 = findViewById(R.id.radio_one);
        radioButton2 = findViewById(R.id.radio_two);

        /*if (radioButton1.isChecked())
        {
            seekbar1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Seed_hour.setEnabled(false);
                    seekbar7.setEnabled(false);
                    return true;

                }
            });
            seekbar8.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar9.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            Toast.makeText(this, "You selected Soil moisture Sensor", Toast.LENGTH_SHORT).show();
        }
        else
        {
            seekbar1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;

                }
            });
            seekbar7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Seed_hour.setEnabled(true);
                    seekbar7.setEnabled(true);
                    return false;

                }
            });
            seekbar8.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });
            seekbar9.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;

                }
            });

            Toast.makeText(this, "You selected Water Pump", Toast.LENGTH_SHORT).show();

        }*/

    }


}