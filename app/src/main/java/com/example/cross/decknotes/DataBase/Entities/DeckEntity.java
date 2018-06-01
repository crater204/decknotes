package com.example.cross.decknotes.DataBase.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "deck_table")
public class DeckEntity
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private Date latestInteraction;

    private int numberOfPlays;

    private int numberOfWins;

    @Ignore
    public DeckEntity(@NonNull String name) {
        this(name, 0 ,0 );
    }

    public DeckEntity(@NonNull String name, int numberOfPlays, int numberOfWins) {
        this.name = name;
        this.numberOfPlays = numberOfPlays;
        this.numberOfWins = numberOfWins;
        this.latestInteraction = new Date();
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

    public int getNumberOfWins()
    {
        return numberOfWins;
    }

    public Date getLatestInteraction() { return latestInteraction; }

    public void setLatestInteraction(Date date) { this.latestInteraction = date; }

    public int getWinPercentage()
    {
        if(numberOfPlays != 0)
        {
            float percentage = ((float)numberOfWins / (float)numberOfPlays * 100);
            return Math.round(percentage);
        }
        else
        {
            return 0;
        }
    }
}
