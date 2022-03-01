package com.example.plantreapp.journals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class JournalsViewModel extends ViewModel {

    private static final String TAG = "JournalViewModel";
    private MutableLiveData<List<Journal>> mutableLiveData;

    public LiveData<List<Journal>> getJournalList() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            initJournalList();
        }
        return mutableLiveData;
    }

    private void initJournalList() {
        List<Journal> journalList = new ArrayList<>();
        //JournalList.add(new Journal("Journal", "description..."));
        mutableLiveData.setValue(journalList);
    }

    public void deleteJournal(int position) {
        if (mutableLiveData.getValue() != null) {
            List<Journal> journalList = new ArrayList<>(mutableLiveData.getValue());
            journalList.remove(position);
            mutableLiveData.setValue(journalList);
        }
    }

    public void addJournal(Journal journal) {
        if (mutableLiveData.getValue() != null) {
            List<Journal> journalList = new ArrayList<>(mutableLiveData.getValue());
            journalList.add(journal);
            mutableLiveData.setValue(journalList);
        }
    }
}
