package com.example.plantreapp.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantreapp.api.APIClient;
import com.example.plantreapp.entities.Plant;

import java.util.List;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;


public class SearchViewModel extends AndroidViewModel {
    private MutableLiveData<List<Plant>> mutableLiveData;
    private Application _application;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        _application = application;
        mutableLiveData = new MutableLiveData<List<Plant>>();
    }

    public LiveData<List<Plant>> getPlantList() {
        return mutableLiveData;
    }

    public void refreshList() {
        APIClient.Companion.invoke(_application);

        APIClient apiClient = new APIClient(_application);

        apiClient.loadPlants(new Continuation<List<? extends Plant>>() {
            @NonNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NonNull Object o) {
                List<Plant> list = (List<Plant>) o;

                mutableLiveData.postValue(list);
            }
        });
    }
}
