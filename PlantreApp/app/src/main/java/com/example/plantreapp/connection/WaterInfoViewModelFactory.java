package com.example.plantreapp.connection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.plantreapp.connection.WaterInfoViewModel;

public class WaterInfoViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;


    public WaterInfoViewModelFactory(Application application){
        mApplication = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WaterInfoViewModel(mApplication);
    }
}

