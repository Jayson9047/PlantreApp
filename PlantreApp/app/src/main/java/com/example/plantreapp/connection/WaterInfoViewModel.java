package com.example.plantreapp.connection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.plantreapp.entities.Moisture;
import com.example.plantreapp.repository.MoistureRepository;

import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

// Extends AndroidViewModel to get the Context
public class WaterInfoViewModel extends AndroidViewModel {
    private MoistureRepository repository;

    public WaterInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new MoistureRepository(application.getApplicationContext());
    }

    public LiveData<List<Moisture>> getMoistureList() {
        return repository.getMoistures();
    }

    public void deleteMoisture(Moisture moisture) {
        repository.delete(moisture, new Continuation<Unit>() {
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

    public void addMoisture(Moisture moisture) {
        repository.insert(moisture, new Continuation<Unit>() {
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

    public void updateMoisture(Moisture moisture) {
        repository.update(moisture, new Continuation<Unit>() {
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
