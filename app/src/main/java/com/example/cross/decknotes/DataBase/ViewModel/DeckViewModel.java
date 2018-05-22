package com.example.cross.decknotes.DataBase.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;
import com.example.cross.decknotes.DeckRepository;

import java.util.Date;
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

    public LiveData<DeckEntity> getDeckById(int id) {
        return repository.getDeckById(id);
    }

    public void insertMatch(int deckId, boolean isWin) {
        System.out.println(new Date());
        RecordEntity record = new RecordEntity(deckId, new Date(), isWin);
        repository.insertMatch(record);
    }

    public void insertDeck(DeckEntity deck) {
        repository.insertDeck(deck);
    }
}
