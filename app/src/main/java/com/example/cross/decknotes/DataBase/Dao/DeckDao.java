package com.example.cross.decknotes.DataBase.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface DeckDao
{
    @Insert
    void insertDeck(DeckEntity deck);

    @Update
    void updateDeck(DeckEntity deck);

    @Delete
    void deleteDeck(DeckEntity deck);

    @Query("SELECT * from deck_table")
    LiveData<List<DeckEntity>> getAllDecks();

    @Query("SELECT * from deck_table WHERE id = :id")
    LiveData<DeckEntity> getDeckById(int id);

    @Query("UPDATE deck_table SET numberOfWins = numberOfWins + 1 WHERE id = :deckId")
    void addWin(int deckId);

    @Query("UPDATE deck_table SET numberOfPlays = numberOfPlays + 1 WHERE id = :deckId")
    void addPlay(int deckId);

    @Query("UPDATE deck_table SET latestInteraction = :date WHERE id = :deckId ")
    void setLatestInteraction(int deckId, Date date);
}
