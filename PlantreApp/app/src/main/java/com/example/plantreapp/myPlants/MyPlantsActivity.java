
package com.example.plantreapp.myPlants;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.example.plantreapp.entities.Plant;
import com.example.plantreapp.journals.JournalsActivity;
import com.example.plantreapp.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/*My Plants Screen*/

public class MyPlantsActivity extends AppCompatActivity
        implements PlantListAdapter.PlantClickInterface {

    private PlantListAdapter plantListAdapter;
    private PlantsViewModel plantsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);

        Intent i = getIntent();

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
                    case R.id.search_item:
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


        plantsViewModel = new ViewModelProvider(this).get(PlantsViewModel.class);
        applyTexts(i);
        plantsViewModel.getPlantList().observe(this, new Observer<List<Plant>>() {
            @Override
            public void onChanged(List<Plant> plants) {
                plantListAdapter.submitList(plants);
            }
        });
    }

    public void addItem(View view) {
        OpenAddPlantActivity();
    }

    public void OpenAddPlantActivity() {
        Intent intent = new Intent(MyPlantsActivity.this, AddPlantActivity.class);
        startActivity(intent);
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

    //@Override
    public void applyTexts(Intent i) {
        PlantInfo plantInfo = i.getParcelableExtra("plantInfo");
        if (plantInfo == null) return;

        Plant plant = new Plant(null,null, null,
                plantInfo.getName(),
                plantInfo.getScifiName(),
                plantInfo.getUri(),
                plantInfo.getDescription(),
                plantInfo.getStage(),
                plantInfo.getSeedWaterRate(),
                plantInfo.getSeedlingWaterRate(),
                plantInfo.getMatureWaterRate(),
                plantInfo.getMinSeedMoisture(),
                plantInfo.getMaxSeedMoisture(),
                plantInfo.getMinSeedlingMoisture(),
                plantInfo.getMaxSeedlingMoisture(),
                plantInfo.getMinMatureMoisture(),
                plantInfo.getMaxMatureMoisture());

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
