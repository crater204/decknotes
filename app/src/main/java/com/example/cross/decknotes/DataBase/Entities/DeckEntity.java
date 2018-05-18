package com.example.cross.decknotes.DataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "deck_table")
public class DeckEntity
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    private int numberOfPlays;

    private int numberOfWins;

    public DeckEntity(@NonNull String name, int numberOfPlays, int numberOfWins) {
        this.name = name;
        this.numberOfPlays = numberOfPlays;
        this.numberOfWins = numberOfWins;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumberOfPlays()
    {
        return numberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays)
    {
        this.numberOfPlays = numberOfPlays;
    }

    public int getNumberOfWins()
    {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins)
    {
        this.numberOfWins = numberOfWins;
    }
}
