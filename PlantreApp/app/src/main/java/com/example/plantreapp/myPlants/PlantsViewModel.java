package com.example.plantreapp.myPlants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlantsViewModel extends ViewModel {

    private static final String TAG = "PlantViewModel";
    private MutableLiveData<List<Plant>> mutableLiveData;

    public LiveData<List<Plant>> getPlantList() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            initPlantList();
        }
        return mutableLiveData;
    }

    private void initPlantList() {
        List<Plant> plantList = new ArrayList<>();
        //plantList.add(new Plant("Plant", "description..."));
        mutableLiveData.setValue(plantList);
    }

    public void deletePlant(int position) {
        if (mutableLiveData.getValue() != null) {
            List<Plant> plantList = new ArrayList<>(mutableLiveData.getValue());
            plantList.remove(position);
            mutableLiveData.setValue(plantList);
        }
    }

    public void addPlant(Plant plant) {
        if (mutableLiveData.getValue() != null) {
            List<Plant> plantList = new ArrayList<>(mutableLiveData.getValue());
            plantList.add(plant);
            mutableLiveData.setValue(plantList);
        }
    }
}
