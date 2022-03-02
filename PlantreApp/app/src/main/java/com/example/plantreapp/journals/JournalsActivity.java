package com.example.plantreapp.journals;

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
import com.example.plantreapp.connection.ConnectionActivity;
import com.example.plantreapp.logs.LogsActivity;
import com.example.plantreapp.myPlants.MyPlantsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/*Journals Screen*/

public class JournalsActivity extends AppCompatActivity implements JournalListAdapter.JournalClickInterface,
        com.example.plantreapp.journals.JournalDialog.JournalDialogListener {

    private JournalListAdapter journalListAdapter;
    private com.example.plantreapp.journals.JournalsViewModel journalsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);

        Intent i = getIntent();
        String title = i.getStringExtra("plantName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title.toUpperCase() + " JOURNALS");


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.my_plants_item);

        // nav click handler
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
        journalListAdapter = new JournalListAdapter( Journal.itemCallback , this);
        recyclerView.setAdapter(journalListAdapter);

        journalsViewModel = new ViewModelProvider(this).get(com.example.plantreapp.journals.JournalsViewModel.class);
        journalsViewModel.getJournalList().observe(this, new Observer<List<Journal>>() {
            @Override
            public void onChanged(List<Journal> journals) {
                journalListAdapter.submitList(journals);
            }
        });
    }

    public void addItem(View view) {
        openDialog();
    }

    public void openDialog() {
        String tag = "Add Journal Dialog";
        com.example.plantreapp.journals.JournalDialog journalDialog = new com.example.plantreapp.journals.JournalDialog();
        journalDialog.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onDelete(int position) {
        journalsViewModel.deleteJournal(position);
    }

    @Override
    public void onSelect(int position, String name) {
        Intent intent = new Intent(JournalsActivity.this, LogsActivity.class);
        intent.putExtra("journalName", name);
        startActivity(intent);
    }

    @Override
    public void applyTexts(String name, String description) {
        Journal journal = new Journal(name, description);
        journalsViewModel.addJournal(journal);
    }
}
