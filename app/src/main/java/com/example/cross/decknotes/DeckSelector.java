package com.example.cross.decknotes;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.ViewModel.DeckViewModel;

import java.util.List;

public class DeckSelector extends AppCompatActivity implements AddDeckDialog.DeckDialogListener
{
    private DeckViewModel deckViewModel;
    private DeckListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_selector);

        deckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
        deckViewModel.getAllDecks().observe(this, new Observer<List<DeckEntity>>()
        {
            @Override
            public void onChanged(@Nullable List<DeckEntity> decks)
            {
                adapter.setDecks(decks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.deck_selector_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DeckListAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(), "hello world! Click!", Toast.LENGTH_SHORT).show();
            }
        }));
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddDeckDialog dialog = new AddDeckDialog();
                dialog.show(getFragmentManager(), "AddDeckDialog");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        EditText deckNameEditText = dialog.getDialog().findViewById(R.id.deck_name_edit_text);
        String deckName = deckNameEditText.getText().toString();
        System.out.println(deckName);
        DeckEntity deck = new DeckEntity(deckName);
        deckViewModel.insert(deck);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        System.out.println("Negative Click Listener");
    }
}
