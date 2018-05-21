package com.example.cross.decknotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cross.decknotes.Data.Deck;
import com.example.cross.decknotes.Data.DeckData;

public class DeckDetails extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_details);

        if(getIntent().hasExtra("DeckIndex")) {
            int index = (int)getIntent().getExtras().get("DeckIndex");
            Toast.makeText(getApplicationContext(), "Details: " + index, Toast.LENGTH_SHORT).show();

            TextView nameTV = findViewById(R.id.detail_name);
            TextView winPercentageTV = findViewById(R.id.detail_win_percentage);
            ProgressBar progressBar = findViewById(R.id.detail_progress_bar);
            Deck deck = DeckData.decks[index];

            nameTV.setText(deck.getName());
            winPercentageTV.setText(String.format(getResources().getString(R.string.win_percentage_message), deck.getWinPercentage()));
            progressBar.setProgress(deck.getWinPercentage());
        } else {
            Toast.makeText(getApplicationContext(), "No Extras", Toast.LENGTH_SHORT).show();
        }
    }
}
