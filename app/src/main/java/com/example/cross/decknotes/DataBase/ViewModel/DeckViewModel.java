package com.example.cross.decknotes.DataBase.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DeckRepository;

import java.util.List;

public class DeckViewModel extends AndroidViewModel
{
    private DeckRepository repository;

    private LiveData<List<DeckEntity>> allDecks;

    public DeckViewModel(Application application) {
        super(application);
        repository = new DeckRepository(application);
        allDecks = repository.getAllDecks();
    }

    public LiveData<List<DeckEntity>> getAllDecks() {
        return allDecks;
    }

    public void insert(DeckEntity deck) {
        repository.insertDeck(deck);
    }
}
