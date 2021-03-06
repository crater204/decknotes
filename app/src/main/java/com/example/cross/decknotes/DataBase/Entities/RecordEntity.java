package com.example.cross.decknotes.DataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "record_table")
public class RecordEntity
{
    @PrimaryKey(autoGenerate = true)
    private int recordId;

    private int deckId;

    @NonNull
    private Date date;

    private boolean isWin;

    public RecordEntity(int deckId, @NonNull Date date, boolean isWin) {
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
    public Date getDate()
    {
        return date;
    }

    public void setDate(@NonNull Date date)
    {
        this.date = date;
    }

    public boolean isWin()
    {
        return isWin;
    }

    public void setIsWin(boolean isWin)
    {
        isWin = isWin;
    }

    public int getRecordId()
    {
        return recordId;
    }

    public void setRecordId(int recordId)
    {
        this.recordId = recordId;
    }
}
