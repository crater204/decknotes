package com.example.cross.decknotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cross.decknotes.Data.Deck;
import com.example.cross.decknotes.Data.DeckData;

import java.util.ArrayList;
import java.util.List;

import static com.example.cross.decknotes.Data.DeckData.decks;

public class DeckSelector extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_selector);

        ArrayList<Deck> list = new ArrayList<>();
        for (int i = 0; i < DeckData.decks.length; i++) {
            list.add(DeckData.decks[i]);
        }
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.deck_selector_line, list);

        ListView listView = findViewById(R.id.decks_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l)
            {
                Intent intent = new Intent(getApplicationContext(), DeckDetails.class);
                intent.putExtra("DeckIndex", index);
                startActivity(intent);
            }
        });
    }

    private class CustomArrayAdapter extends ArrayAdapter<Deck>
    {
        public CustomArrayAdapter(@NonNull Context context, int resourceId, List<Deck> objects)
        {
            super(context, resourceId, objects);
        }
    }
}
