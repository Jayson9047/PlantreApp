
package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantreapp.R;
import com.example.plantreapp.connection.ConnBtnActivity;
import com.example.plantreapp.connection.ConnectionActivity;
import com.example.plantreapp.entities.Plant;
import com.example.plantreapp.journals.JournalsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/*My Plants Screen*/

public class MyPlantsActivity extends AppCompatActivity
        implements com.example.plantreapp.myPlants.PlantListAdapter.PlantClickInterface,
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
                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext(), ConnBtnActivity.class));
                        return true;
                    case R.id.my_plants_item:
                        return true;
                    case R.id.journals_item:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
        plantListAdapter = new PlantListAdapter( Plant.Companion.getItemCallback(), this);
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
    public void onDelete(Plant plant) {
        plantsViewModel.deletePlant(plant);
    }

    @Override
    public void onSelect(Plant plant) {
        Intent intent = new Intent(MyPlantsActivity.this, JournalsActivity.class);
        intent.putExtra("plantName", plant.getName());
        intent.putExtra("plantUid", plant.getUid());
        startActivity(intent);
    }

    @Override
    public void applyTexts(String name, String description) {
        // To properly Create a new plant we need more values - rate is in hours and moisture are percentages
        Plant plant = new Plant(null, name, "scifiName", "URI to picture", description, "seed", 12, 48, 168, 80,90, 60, 80,50, 70);
        plantsViewModel.addPlant(plant);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/
}
