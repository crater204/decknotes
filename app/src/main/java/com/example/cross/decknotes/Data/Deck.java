package com.example.cross.decknotes.Data;

import android.util.Log;

public class Deck
{
    private String name;
    private int numberOfPlays;
    private int numberOfWins;

    public Deck(String name)
    {
        this(name, 0, 0);
    }

    public Deck(String name, int numberOfPlays, int numberOfWins)
    {
        this.name = name;
        this.numberOfPlays = numberOfPlays;
        this.numberOfWins = numberOfWins;
    }

    public void addWin()
    {
        numberOfPlays++;
        numberOfWins++;
    }

    public void addLoss()
    {
        numberOfPlays++;
    }

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return name;
    }
}
