package com.example.plantreapp.search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.plantreapp.R;
import com.example.plantreapp.entities.PlantInfo;
import com.example.plantreapp.myPlants.AddPlantActivity;
import com.example.plantreapp.myPlants.MyPlantsActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class DBPlantInfoActivity extends AppCompatActivity {
    private PlantInfo _plantInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_plant_info);

        Intent i = getIntent();
        _plantInfo = i.getParcelableExtra("PLANTINFO");


        ImageView imageView = (ImageView) findViewById(R.id.dbPlantInfoImage);
        TextView textView = (TextView) findViewById(R.id.textPlantInfoDescription);

        textView.setText(_plantInfo.getDescription());
        Log.d("eee", _plantInfo.getPictures().get(0));
        Glide.with(this).load(_plantInfo.getPictures().get(0)).into(imageView);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddPlantActivity.class);
        intent.putExtra("PLANTINFO", _plantInfo);
        startActivity(intent);
    }


}
