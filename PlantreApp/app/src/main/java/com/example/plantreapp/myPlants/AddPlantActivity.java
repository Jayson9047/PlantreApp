package com.example.plantreapp.myPlants;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.plantreapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddPlantActivity extends AppCompatActivity {
    private Button addBtn, cancelBtn;

    private EditText nameTxt, scifiNameTxt, descriptionTxt;

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


    private ImageView plantPic;
    private Button selectImgBtn;

    //permission constant
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final int IMAGE_FROM_GALLERY_CODE = 300;
    private static final int IMAGE_FROM_CAMERA_CODE = 400;

    // string array of permission
    private String[] cameraPermission;
    private String[] storagePermission;

    //Image uri var
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Plant");

        //init permission
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //init view
        plantPic = findViewById(R.id.plantPic);

        selectImgBtn = findViewById(R.id.select_img_button);
        selectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        nameTxt = findViewById(R.id.editPlantName);
        scifiNameTxt = findViewById(R.id.editScifiName);
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
                uri = imageUri.toString();
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



    private void showImagePickerDialog() {

        //option for dialog
        String[] options = {"Camera", "Gallery"};

        // Alert dialog builder
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);

        //setTitle
        builder.setTitle("Choose An Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle item click
                if (which == 0){ //start from 0 index
                    //camera selected
                    if (!checkCameraPermission()){
                        //request camera permission
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                }else if (which == 1){
                    //Gallery selected
                    /*if (!checkStoragePermission()){
                        //request storage permission
                        requestStoragePermission();
                    } else {*/
                    pickFromGallery();
                    // }

                }
            }
        }).create().show();
    }

    private void pickFromGallery() {
        //intent for taking image from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*"); // only Image

        startActivityForResult(galleryIntent,IMAGE_FROM_GALLERY_CODE);
    }

    private void pickFromCamera() {

//       ContentValues for image info
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE_DETAIL");

        //save imageUri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //intent to open camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);

        startActivityForResult(cameraIntent,IMAGE_FROM_CAMERA_CODE);
    }

    private void saveData() {
        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
    }

    //back button click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //check camera permission
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result & result1;
    }

    //request for camera permission
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION_CODE); // handle request permission on override method
    }

    //check storage permission
    private boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
    }

    //request for storage permission
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION_CODE);
    }


    //handle request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length >0){

                    //if all permission allowed return true , otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        //both permission granted
                        pickFromCamera();
                    }else {
                        //permission not granted
                        Toast.makeText(getApplicationContext(), "Camera & Storage Permission needed...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_PERMISSION_CODE:
                if (grantResults.length >0){

                    //if all permission allowed return true , otherwise false
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted){
                        //permission granted
                        pickFromGallery();
                    }else {
                        //permission not granted
                        Toast.makeText(getApplicationContext(), "Storage Permission needed...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_FROM_GALLERY_CODE){
                // picked image from gallery
                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddPlantActivity.this);

            }else if (requestCode == IMAGE_FROM_CAMERA_CODE){
                //picked image from camera
                //crop Image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddPlantActivity.this);
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                //cropped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                plantPic.setImageURI(imageUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //for error handling
                Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}