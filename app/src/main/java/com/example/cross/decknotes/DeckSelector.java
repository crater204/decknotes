package com.example.cross.decknotes;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.ViewModel.DeckViewModel;
import com.example.cross.decknotes.Dialogs.AddDeckDialog;
import com.example.cross.decknotes.Dialogs.EmailDialog;

import java.util.List;

public class DeckSelector extends AppCompatActivity implements AddDeckDialog.DeckDialogListener
{
    private DeckViewModel deckViewModel;
    private DeckListAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_contact_me:
                EmailDialog email = new EmailDialog();
                email.show(getFragmentManager(), "EmailDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        final RecyclerView recyclerView = findViewById(R.id.deck_selector_recycler_view);
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
                Intent i = new Intent(getApplicationContext(), DeckDetails.class);
                DeckEntity deck = adapter.getDeck(position);
                i.putExtra("DeckId", deck.getId());
                startActivity(i);
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
        switch(dialog.getTag())
        {
            case "AddDeckDialog":
                EditText deckNameEditText = dialog.getDialog().findViewById(R.id.deck_name_edit_text);
                String deckName = deckNameEditText.getText().toString();
                System.out.println(deckName);
                DeckEntity deck = new DeckEntity(deckName);
                deckViewModel.insertDeck(deck);
                break;
            case "EmailDialog":
                Spinner spinner = dialog.getDialog().findViewById(R.id.email_spinner);
                String emailSubject = (String)spinner.getSelectedItem();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"decknotes@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(DeckSelector.this, "No email app present :( . Send an email to decknotes@gmail.com", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        // Nothing special needs to happen. The dialog dismisses itself
    }
}
