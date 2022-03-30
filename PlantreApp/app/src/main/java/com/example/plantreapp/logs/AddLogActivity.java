package com.example.plantreapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plantreapp.R;

import java.time.LocalDate;
import java.util.Calendar;

public class AddLogActivity extends AppCompatActivity {

    private EditText logName, logInfo;
    private Button addBtn;
    private String journalName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADD NOTE");

        Intent i = getIntent();
        journalName = i.getStringExtra("journalName");

        logName = findViewById(R.id.log_name);
        logInfo = findViewById(R.id.log_description);
        addBtn = findViewById(R.id.add_log_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = logName.getText().toString();
                String info = logInfo.getText().toString();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int day = Calendar.getInstance().get(Calendar.DATE);

                String date = Integer.toString(year);
                if(month < 10) {
                    date += "-0" + month;
                } else {
                    date += "-" + month;
                }
                if(day < 10) {
                    date += "-0" + day;
                } else {
                    date += "-" + day;
                }

                Intent intent = new Intent(AddLogActivity.this, LogsActivity.class);
                intent.putExtra("newNoteName", name);
                intent.putExtra("newNoteInfo", info);
                intent.putExtra("journalName", journalName);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}