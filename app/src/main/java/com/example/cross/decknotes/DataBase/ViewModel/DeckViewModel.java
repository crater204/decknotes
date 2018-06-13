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

    public DeckViewModel(Application application)
    {
        super(application);
        repository = new DeckRepository(application);
        allDecks = repository.getAllDecks();
    }

    public LiveData<List<DeckEntity>> getAllDecks()
    {
        return allDecks;
    }

    public LiveData<DeckEntity> getDeckById(int id)
    {
        return repository.getDeckById(id);
    }

    public LiveData<List<RecordEntity>> getRecordsForDeck(int deckId)
    {
        return repository.getRecordsByDeckId(deckId);
    }

    public void editDeckName(DeckEntity deck, String newName) {
        deck.setName(newName);
        repository.updateDeck(deck);
    }

    public RecordEntity insertMatch(int deckId, boolean isWin)
    {
        RecordEntity record = new RecordEntity(deckId, new Date(), isWin);
        repository.insertMatch(record);
        return record;
    }

    public void deleteDeck(DeckEntity deck) {
        repository.deleteDeck(deck);
    }

    public void deleteRecord(RecordEntity record) {
        repository.deleteRecord(record);
    }

    public void insertTestMatches(int deckId)
    {
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 12), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 12), false));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 12), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 12), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), false));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), false));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 15), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 16), true));
        repository.insertMatch(new RecordEntity(deckId, new Date(2018, 4, 16), false));
    }

    public void insertDeck(DeckEntity deck)
    {
        repository.insertDeck(deck);
    }
}
