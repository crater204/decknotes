package com.example.cross.decknotes.DataBase.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.cross.decknotes.DataBase.Entities.RecordEntity;

import java.util.List;

@Dao
public interface RecordDao
{
    @Insert
    void insert(RecordEntity record);

    @Query("SELECT * from record_table WHERE id = :id")
    List<RecordEntity> getRecordsForId(int id);
}