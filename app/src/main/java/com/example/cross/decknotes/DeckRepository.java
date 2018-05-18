package com.example.cross.decknotes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.cross.decknotes.Data.Deck;
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

    public DeckRepository(Application application) {
        DeckRoomDatabase db = DeckRoomDatabase.getDatabase(application);
        deckDao = db.deckDao();
        recordDao = db.recordDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<DeckEntity>> getAllDecks() {
        return allDecks;
    }

    public void insertDeck(DeckEntity deck) {
        new InsertDeckAsyncTask(deckDao).execute(deck);
    }

    public void insertRecord(RecordEntity record) {
        new InsertRecordAsyncTask(recordDao).execute(record);
    }

    private static class InsertDeckAsyncTask extends AsyncTask<DeckEntity, Void, Void>
    {

        private DeckDao mAsyncTaskDao;

        InsertDeckAsyncTask(DeckDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DeckEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertRecordAsyncTask extends AsyncTask<RecordEntity, Void, Void>
    {

        private RecordDao mAsyncTaskDao;

        InsertRecordAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
