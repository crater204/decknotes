package com.example.cross.decknotes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.ViewModel.DeckViewModel;

public class DeckDetails extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_details);

        if(getIntent().hasExtra("DeckId"))
        {
            int id = (int)getIntent().getExtras().get("DeckId");
            Toast.makeText(getApplicationContext(), "Details: " + id, Toast.LENGTH_SHORT).show();

            DeckViewModel deckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
            deckViewModel.getDeckById(id).observe(this, new Observer<DeckEntity>()
            {
                @Override
                public void onChanged(@Nullable DeckEntity deck)
                {
                    if(deck != null)
                    {
                        TextView nameTV = findViewById(R.id.detail_name);
                        TextView winPercentageTV = findViewById(R.id.detail_win_percentage);
                        ProgressBar progressBar = findViewById(R.id.detail_progress_bar);

                        nameTV.setText(deck.getName());
                        winPercentageTV.setText(String.format(getResources().getString(R.string.win_percentage_message), deck.getWinPercentage()));
                        progressBar.setProgress(deck.getWinPercentage());
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Extras", Toast.LENGTH_SHORT).show();
        }
    }
}
