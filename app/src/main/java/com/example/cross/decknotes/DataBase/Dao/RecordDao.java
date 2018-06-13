package com.example.cross.decknotes.DataBase.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.cross.decknotes.DataBase.Entities.RecordEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface RecordDao
{
    @Insert
    void insert(RecordEntity record);

    @Query("SELECT * from record_table WHERE deckId = :deckId")
    LiveData<List<RecordEntity>> getRecordsForDeck(int deckId);

    @Query("DELETE from record_table WHERE deckId = :deckId")
    void deleteRecords(int deckId);

    //TODO: Find a way to do this with a record ID
    @Query("DELETE from record_table WHERE (date = :date AND isWin = :isWin AND deckId = :deckId)")
    void deleteRecord(Date date, boolean isWin, int deckId);
}
