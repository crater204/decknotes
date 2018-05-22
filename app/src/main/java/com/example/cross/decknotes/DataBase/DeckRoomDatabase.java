package com.example.cross.decknotes.DataBase;

import android.arch.persistence.room.*;
import android.content.Context;

import com.example.cross.decknotes.DataBase.Dao.DeckDao;
import com.example.cross.decknotes.DataBase.Dao.RecordDao;
import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;
import com.example.cross.decknotes.TypeConverters.DateTypeConverter;

@Database(entities = {DeckEntity.class, RecordEntity.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
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
