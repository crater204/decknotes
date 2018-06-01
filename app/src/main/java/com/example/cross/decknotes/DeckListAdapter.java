package com.example.cross.decknotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.DeckViewHolder>
{
    class DeckViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView deckName;
        private final TextView winPercentage;

        private DeckViewHolder(View itemView)
        {
            super(itemView);
            deckName = itemView.findViewById(R.id.deck_selector_text_view);
            winPercentage = itemView.findViewById(R.id.deck_selector_win_percentage);
        }
    }

    private final LayoutInflater inflater;
    private List<DeckEntity> decks;

    DeckListAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeckViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.deck_selector_line, parent, false);
        return new DeckViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeckViewHolder holder, int position)
    {
        if(decks != null)
        {
            DeckEntity current = decks.get(position);
            holder.deckName.setText(current.getName());
            holder.winPercentage.setText(current.getWinPercentage() + "%");
        }
        else
        {
            holder.deckName.setText("No Deck");
            holder.winPercentage.setText("");
        }
    }

    DeckEntity getDeck(int position) {
        return decks.get(position);
    }

    void setDecks(List<DeckEntity> decks)
    {
        this.decks = decks;
        Collections.sort(this.decks, new Comparator<DeckEntity>()
        {
            @Override
            public int compare(DeckEntity d1, DeckEntity d2)
            {
                // The sort is intentionally done backwards so that we sort in descending order
                // AKA: We have the newest on top and oldest on the bottom
                return d2.getLatestInteraction().compareTo(d1.getLatestInteraction());
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if(decks != null)
        {
            return decks.size();
        }
        else
        {
            return 0;
        }
    }
}
