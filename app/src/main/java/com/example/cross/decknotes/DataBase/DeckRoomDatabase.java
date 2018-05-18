package com.example.cross.decknotes.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.cross.decknotes.DataBase.Dao.DeckDao;
import com.example.cross.decknotes.DataBase.Dao.RecordDao;
import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;

@Database(entities = {DeckEntity.class, RecordEntity.class}, version = 1)
public abstract class DeckRoomDatabase extends RoomDatabase
{
    public abstract DeckDao deckDao();
    public abstract RecordDao recordDao();

    private static DeckRoomDatabase INSTANCE;

    public static DeckRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized(DeckRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeckRoomDatabase.class, "deck_database")
                                       .build();
                }
            }
        }
        return INSTANCE;
    }
}
