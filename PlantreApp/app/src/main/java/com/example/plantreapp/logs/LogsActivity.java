
package com.example.plantreapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;
import com.example.plantreapp.connection.ConnBtnActivity;
import com.example.plantreapp.connection.ConnectionActivity;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/*Logs Screen*/

public class LogsActivity extends AppCompatActivity implements com.example.plantreapp.logs.LogListAdapter.LogClickInterface {

    private com.example.plantreapp.logs.LogListAdapter logListAdapter;
    private LogsViewModel logsViewModel;

    private String journalName = "";

    //private static String logName;
    //private static String logInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        Intent i = getIntent();

        String title = i.getStringExtra("journalName");
        journalName = title;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title.toUpperCase() + " LOGS");


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
                    case R.id.journals_item:
                        //startActivity(new Intent(getApplicationContext(), Search.class));
                        return true;
                    case R.id.connection_item:
                        startActivity(new Intent(getApplicationContext(), ConnectionActivity.class));
                        return true;
                }
                return false;
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        logListAdapter = new com.example.plantreapp.logs.LogListAdapter( com.example.plantreapp.logs.Log.itemCallback , this);
        recyclerView.setAdapter(logListAdapter);

        logsViewModel = new ViewModelProvider(this).get(LogsViewModel.class);
        logsViewModel.getLogList().observe(this, new Observer<List<com.example.plantreapp.logs.Log>>() {
            @Override
            public void onChanged(List<com.example.plantreapp.logs.Log> logs) {
                logListAdapter.submitList(logs);
            }
        });

        if (i.getStringExtra("newNoteName") != null) {
            String name = i.getStringExtra("newNoteName");
            String info = i.getStringExtra("newNoteInfo");
            com.example.plantreapp.logs.Log log = new com.example.plantreapp.logs.Log(name, info);
            logsViewModel.addLog(log);

            //logName = name;
            //logInfo = info;
        }
    }

    public void addItem(View view) {
        Intent intent = new Intent(LogsActivity.this, AddLogActivity.class);
        intent.putExtra("journalName", journalName);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
        logsViewModel.deleteLog(position);
    }

    @Override
    public void onSelect(int position, String name) {
        Intent intent = new Intent(LogsActivity.this, com.example.plantreapp.logs.NoteActivity.class);

        //intent.putExtra("logID", id);

        startActivity(intent);
    }
}
