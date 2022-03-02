
package com.example.plantreapp.myPlants;

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
import com.example.plantreapp.journals.JournalsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/*My Plants Screen*/

public class MyPlantsActivity extends AppCompatActivity implements com.example.plantreapp.myPlants.PlantListAdapter.PlantClickInterface,
        PlantDialog.PlantDialogListener {

    private com.example.plantreapp.myPlants.PlantListAdapter plantListAdapter;
    private com.example.plantreapp.myPlants.PlantsViewModel plantsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        // set actionbar title to "my plants"
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MY PLANTS");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.my_plants_item);

        // nav click handler
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.my_plants_item:
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

        // Toast.makeText(Home.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        plantListAdapter = new com.example.plantreapp.myPlants.PlantListAdapter( Plant.itemCallback , this);
        recyclerView.setAdapter(plantListAdapter);

        plantsViewModel = new ViewModelProvider(this).get(com.example.plantreapp.myPlants.PlantsViewModel.class);
        plantsViewModel.getPlantList().observe(this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                plantListAdapter.submitList(plants);
            }
        });
    }

    public void addItem(View view) {
        openDialog();
    }

    public void openDialog() {
        String tag = "Add Plant Dialog";
        PlantDialog plantDialog = new PlantDialog();
        plantDialog.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void onDelete(int position) {
        plantsViewModel.deletePlant(position);
    }

    @Override
    public void onSelect(int position, String name) {
        Intent intent = new Intent(MyPlantsActivity.this, JournalsActivity.class);
        intent.putExtra("plantName", name);
        startActivity(intent);
    }

    @Override
    public void applyTexts(String name, String description) {
        Plant plant = new Plant(name, description);
        plantsViewModel.addPlant(plant);
    }
}
