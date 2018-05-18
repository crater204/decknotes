package com.example.cross.decknotes.DataBase.Entities;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "record_table")
public class RecordEntity
{
    private int id;

    @NonNull
    private Date date;

    private boolean isWin;

    public RecordEntity(int id, @NonNull Date date, boolean isWin) {
        this.id = id;
        this.date = date;
        this.isWin = isWin;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public void setWin(boolean win)
    {
        isWin = win;
    }
}
