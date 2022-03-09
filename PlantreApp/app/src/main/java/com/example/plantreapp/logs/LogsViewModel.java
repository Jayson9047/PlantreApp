package com.example.plantreapp.logs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.plantreapp.entities.Log;
import com.example.plantreapp.repository.LogRepository;

import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class LogsViewModel extends AndroidViewModel {

    private static final String TAG = "LogViewModel";
    private MutableLiveData<List<Log>> mutableLiveData;
    private LogRepository repository;

    public LogsViewModel(@NonNull Application application) {
        super(application);
        repository = new LogRepository(application.getApplicationContext());
    }

    public LiveData<List<Log>> getLogList() {
        return repository.getLogs();
    }

    public void deleteLog(Log log) {
        repository.delete(log, new Continuation<Unit>() {
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

    public void addLog(Log log) {
        repository.insert(log, new Continuation<Unit>() {
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
