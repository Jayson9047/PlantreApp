package com.example.plantreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

public class ConnBtnActivity extends AppCompatActivity {

    Button ButtonConnectionPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_btn);

        ButtonConnectionPage = (Button) findViewById(R.id.btnConnPage);

        ButtonConnectionPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConnBtnActivity.this, ConnectionActivity.class);
                startActivity(intent);
            }
        });
    }
}