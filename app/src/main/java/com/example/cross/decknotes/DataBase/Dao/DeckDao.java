package com.example.cross.decknotes.DataBase.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.cross.decknotes.Data.Deck;
import com.example.cross.decknotes.DataBase.Entities.DeckEntity;

import java.util.List;

@Dao
public interface DeckDao
{
    @Insert
    void insert(DeckEntity deck);

    @Query("SELECT * from deck_table")
    LiveData<List<DeckEntity>> getAllDecks();

}