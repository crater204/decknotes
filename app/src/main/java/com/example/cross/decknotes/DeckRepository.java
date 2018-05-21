package com.example.cross.decknotes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.cross.decknotes.DataBase.Dao.DeckDao;
import com.example.cross.decknotes.DataBase.Dao.RecordDao;
import com.example.cross.decknotes.DataBase.DeckRoomDatabase;
import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;

import java.util.List;

public class DeckRepository
{
    private DeckDao deckDao;
    private RecordDao recordDao;
    private LiveData<List<DeckEntity>> allDecks;

    public DeckRepository(Application application)
    {
        DeckRoomDatabase db = DeckRoomDatabase.getDatabase(application);
        deckDao = db.deckDao();
        recordDao = db.recordDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<DeckEntity>> getAllDecks()
    {
        return allDecks;
    }

    public void insertDeck(DeckEntity deck)
    {
        new InsertDeckAsyncTask(deckDao).execute(deck);
    }

    public void insertRecord(RecordEntity record)
    {
        new InsertRecordAsyncTask(recordDao).execute(record);
    }

    public LiveData<DeckEntity> getDeckById(int id)
    {
        return deckDao.getDeckById(id);
    }

    private static class InsertDeckAsyncTask extends AsyncTask<DeckEntity, Void, Void>
    {
        private DeckDao asyncTaskDao;

        InsertDeckAsyncTask(DeckDao dao)
        {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DeckEntity... params)
        {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertRecordAsyncTask extends AsyncTask<RecordEntity, Void, Void>
    {
        private RecordDao asyncTaskDao;

        InsertRecordAsyncTask(RecordDao dao)
        {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordEntity... params)
        {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
