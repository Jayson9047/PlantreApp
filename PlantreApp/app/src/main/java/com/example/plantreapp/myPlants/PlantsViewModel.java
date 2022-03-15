package com.example.plantreapp.myPlants;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantreapp.entities.Plant;
import com.example.plantreapp.repository.PlantRepository;

import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

// Extends AndroidViewModel to get the Context
public class PlantsViewModel extends AndroidViewModel {

    private static final String TAG = "PlantViewModel";
    private MutableLiveData<List<Plant>> mutableLiveData;
    private PlantRepository repository;

    public PlantsViewModel(@NonNull Application application) {
        super(application);
        repository = new PlantRepository(application.getApplicationContext());
    }

    // Every LiveData<Plant, Journal, Logs> - Should be able to be replaced with my files in the entities folder
    // ... You should be able to delete all the models that satbir has and replace them with the ones in entities.
    public LiveData<List<Plant>> getPlantList() {
        return repository.getPlants();
    }

    // this position variable can it be replaced with the uuid from the list?
    public void deletePlant(Plant plant) {
        repository.delete(plant, new Continuation<Unit>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE; // Default to this value, generated snippet is nul
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                // Handle on delete... display a toast

            }
        });
    }

    public void addPlant(Plant plant) {
        repository.insert(plant, new Continuation<Unit>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                // Handle on delete... display a toast

            }
        });
    }
}
