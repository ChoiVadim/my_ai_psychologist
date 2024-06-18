package com.choivadim.my_ai_psychologist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalEntryDao journalEntryDao;
    private LiveData<List<JournalEntry>> allEntries;

    public JournalViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        journalEntryDao = database.journalEntryDao();
        allEntries = journalEntryDao.getAllEntries();
    }

    public void insert(JournalEntry entry) {
        new Thread(() -> journalEntryDao.insert(entry)).start();
    }

    public void delete(JournalEntry entry) {
        new Thread(() -> journalEntryDao.delete(entry)).start();
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries;
    }
}
