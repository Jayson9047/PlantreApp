package com.example.plantreapp.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantreapp.entities.Plant;

import java.util.List;


public class SearchViewModel extends AndroidViewModel {
    private MutableLiveData<List<Plant>> mutableLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData = new MutableLiveData<List<Plant>>();
    }

    public LiveData<List<Plant>> getPlantList() {
        return mutableLiveData;
    }
}
