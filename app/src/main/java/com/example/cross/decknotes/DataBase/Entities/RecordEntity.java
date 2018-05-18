package com.example.cross.decknotes.DataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "record_table")
public class RecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int recordId;

    private int deckId;

    public int getRecordId()
    {
        return recordId;
    }

    public void setRecordId(int recordId)
    {
        this.recordId = recordId;
    }

    @NonNull
    private String date;

    private boolean isWin;

    public RecordEntity(int deckId, @NonNull String date, boolean isWin) {
        this.deckId = deckId;
        this.date = date;
        this.isWin = isWin;
    }

    public int getDeckId()
    {
        return deckId;
    }

    public void setDeckId(int deckId)
    {
        this.deckId = deckId;
    }

    @NonNull
    public String getDate()
    {
        return date;
    }

    public void setDate(@NonNull String date)
    {
        this.date = date;
    }

    public boolean isWin()
    {
        return isWin;
    }

    public void setWin(boolean win)
    {
        isWin = win;
    }
}
