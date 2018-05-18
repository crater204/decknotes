package com.example.cross.decknotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


public class DeckSelector extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_selector);

        FloatingActionButton fab = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.deck_selector_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DeckListAdapter adapter = new DeckListAdapter(this, new CustomClickListener());
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // TODO: Create a dialog and that adds a new deck to the database
            }
        });
    }

    private class CustomClickListener implements DeckListAdapter.RecyclerViewClickListener {
        @Override
        public void onClick(View view, int position)
        {
            Toast.makeText(getApplicationContext(), "CLICK!!! " + position, Toast.LENGTH_SHORT).show();
            /*
            Intent intent = new Intent(getApplicationContext(), DeckDetails.class);
            intent.putExtra("DeckIndex", position);
            startActivity(intent);
            */
        }
    }
}
