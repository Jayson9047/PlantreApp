package com.example.plantreapp.logs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class LogsViewModel extends ViewModel {

    private static final String TAG = "LogViewModel";
    private MutableLiveData<List<com.example.plantreapp.logs.Log>> mutableLiveData;

    public LiveData<List<com.example.plantreapp.logs.Log>> getLogList() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            initLogList();
        }
        return mutableLiveData;
    }

    private void initLogList() {
        List<com.example.plantreapp.logs.Log> logList = new ArrayList<>();
        //LogList.add(new Log("Log", "description..."));
        mutableLiveData.setValue(logList);
    }

    public void deleteLog(int position) {
        if (mutableLiveData.getValue() != null) {
            List<com.example.plantreapp.logs.Log> logList = new ArrayList<>(mutableLiveData.getValue());
            logList.remove(position);
            mutableLiveData.setValue(logList);
        }
    }

    public void addLog(com.example.plantreapp.logs.Log log) {
        if (mutableLiveData.getValue() != null) {
            List<com.example.plantreapp.logs.Log> logList = new ArrayList<>(mutableLiveData.getValue());
            logList.add(log);
            mutableLiveData.setValue(logList);
        }
    }
}
