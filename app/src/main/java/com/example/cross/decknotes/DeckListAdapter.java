package com.example.cross.decknotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;

import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.DeckViewHolder>
{
    class DeckViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RecyclerViewClickListener listener;
        private final TextView deckItemView;

        private DeckViewHolder(View itemView, RecyclerViewClickListener listener)
        {
            super(itemView);
            this.listener = listener;
            deckItemView = itemView.findViewById(R.id.deck_selector_text_view);
        }

        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());
        }
    }

    interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    private RecyclerViewClickListener listener;
    private final LayoutInflater inflater;
    private List<DeckEntity> decks;

    DeckListAdapter(Context context, RecyclerViewClickListener listener)
    {
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DeckViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = inflater.inflate(R.layout.deck_selector_line, parent, false);
        return new DeckViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(DeckViewHolder holder, int position)
    {
        if(decks != null)
        {
            DeckEntity current = decks.get(position);
            holder.deckItemView.setText(current.getName());
        }
        else
        {
            holder.deckItemView.setText("No Deck");
        }
    }

    void setDecks(List<DeckEntity> decks)
    {
        this.decks = decks;
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
