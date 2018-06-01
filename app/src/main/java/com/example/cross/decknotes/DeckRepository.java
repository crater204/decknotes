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

    public void insertDeck(DeckEntity deck)
    {
        new InsertDeckAsyncTask(deckDao).execute(deck);
    }

    public void updateDeck(DeckEntity updatedDeck)
    {
        new UpdateDeckAsyncTask(deckDao).execute(updatedDeck);
    }

    public void deleteDeck(DeckEntity deck)
    {
        new DeleteDeckAsyncTask(deckDao, recordDao).execute(deck);
    }

    public void insertMatch(RecordEntity record)
    {
        new InsertRecordAsyncTask(recordDao, deckDao).execute(record);
    }

    public LiveData<List<DeckEntity>> getAllDecks()
    {
        return allDecks;
    }

    public LiveData<DeckEntity> getDeckById(int id)
    {
        return deckDao.getDeckById(id);
    }

    public LiveData<List<RecordEntity>> getRecordsByDeckId(int deckId)
    {
        return recordDao.getRecordsForDeck(deckId);
    }

    private static class DeleteDeckAsyncTask extends AsyncTask<DeckEntity, Void, Void>
    {
        private DeckDao deckDao;
        private RecordDao recordDao;

        DeleteDeckAsyncTask(DeckDao deck, RecordDao record)
        {
            deckDao = deck;
            recordDao = record;
        }

        @Override
        protected Void doInBackground(DeckEntity... deckEntities)
        {
            deckDao.deleteDeck(deckEntities[0]);
            recordDao.deleteRecords(deckEntities[0].getId());
            return null;
        }
    }

    private static class InsertDeckAsyncTask extends AsyncTask<DeckEntity, Void, Void>
    {
        private DeckDao deckDao;

        InsertDeckAsyncTask(DeckDao dao)
        {
            deckDao = dao;
        }

        @Override
        protected Void doInBackground(final DeckEntity... params)
        {
            deckDao.insertDeck(params[0]);
            return null;
        }
    }

    private static class InsertRecordAsyncTask extends AsyncTask<RecordEntity, Void, Void>
    {
        private RecordDao recordDao;
        private DeckDao deckDao;

        InsertRecordAsyncTask(RecordDao record, DeckDao deck)
        {
            recordDao = record;
            deckDao = deck;
        }

        @Override
        protected Void doInBackground(final RecordEntity... params)
        {
            RecordEntity record = params[0];
            recordDao.insert(record);
            if(record.isWin())
            {
                deckDao.addWin(record.getDeckId());
            }
            deckDao.addPlay(record.getDeckId());
            return null;
        }
    }

    private static class UpdateDeckAsyncTask extends AsyncTask<DeckEntity, Void, Void>
    {
        private DeckDao deckDao;

        UpdateDeckAsyncTask(DeckDao dao)
        {
            deckDao = dao;
        }

        @Override
        protected Void doInBackground(DeckEntity... deckEntities)
        {
            deckDao.updateDeck(deckEntities[0]);
            return null;
        }
    }
}
