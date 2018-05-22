package com.example.cross.decknotes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;
import com.example.cross.decknotes.DataBase.ViewModel.DeckViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeckDetails extends AppCompatActivity
{
    private DeckEntity deck;
    private DeckViewModel deckViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_details);

        if(getIntent().hasExtra("DeckId"))
        {
            int id = (int)getIntent().getExtras().get("DeckId");
            Toast.makeText(getApplicationContext(), "Details: " + id, Toast.LENGTH_SHORT).show();

            deckViewModel = ViewModelProviders.of(this).get(DeckViewModel.class);
            deckViewModel.getDeckById(id).observe(this, new Observer<DeckEntity>()
            {
                @Override
                public void onChanged(@Nullable DeckEntity deckEntity)
                {
                    if(deckEntity != null)
                    {
                        deck = deckEntity;
                        TextView nameTV = findViewById(R.id.detail_name);
                        TextView winPercentageTV = findViewById(R.id.detail_win_percentage);

                        nameTV.setText(deck.getName());
                        winPercentageTV.setText(String.format(getResources().getString(R.string.win_percentage_message), deck.getWinPercentage()));


                        deckViewModel.getRecordsForDeck(deck.getId())
                                .observe(DeckDetails.this, new Observer<List<RecordEntity>>()
                                {
                                    @Override
                                    public void onChanged(@Nullable List<RecordEntity> recordEntities)
                                    {
                                        if(recordEntities != null)
                                        {
                                            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy", Locale.US);
                                            ArrayList<String> dates = new ArrayList<>();

                                            for(int i = 0; i < recordEntities.size(); i++)
                                            {
                                                String formattedDate = dateFormatter.format(recordEntities.get(i)
                                                                                                    .getDate());
                                                if(!dates.contains(formattedDate))
                                                {
                                                    dates.add(formattedDate);
                                                }
                                            }
                                            // Shrink it to just the last 4
                                            if(dates.size() > 4)
                                            {
                                                ArrayList<String> temp = new ArrayList<>();
                                                for(int j = 4; j >= 1; j--)
                                                {
                                                    temp.add(dates.get(j));
                                                }
                                                dates = temp;
                                            }
                                            ArrayList<BarEntry> barEntries = new ArrayList<>();
                                            for(int dateIndex = 0; dateIndex < dates.size(); dateIndex++)
                                            {
                                                int winCount = 0;
                                                int playCount = 0;
                                                for(int recordIndex = 0;
                                                        recordIndex < recordEntities.size(); recordIndex++)
                                                {
                                                    if(dateFormatter.format(recordEntities.get(recordIndex).getDate())
                                                               .equals(dates.get(dateIndex)))
                                                    {
                                                        if(recordEntities.get(recordIndex).isWin())
                                                        {
                                                            winCount++;
                                                        }
                                                        playCount++;
                                                    }
                                                }
                                                int value = (int)(((double)winCount / (double)playCount) * 100);
                                                barEntries.add(new BarEntry(dateIndex, value));
                                            }
                                            BarDataSet dataSet = new BarDataSet(barEntries, "Win Percentages");
                                            BarData data = new BarData(dataSet);
                                            data.setBarWidth(0.9f);
                                            data.setValueTextSize(16);
                                            BarChart chart = findViewById(R.id.bar_chart);
                                            chart.setData(data);
                                            chart.getAxisRight().setEnabled(false);
                                            YAxis leftAxis = chart.getAxisLeft();
                                            leftAxis.setAxisMaxValue(100);
                                            leftAxis.setAxisMinValue(0);
                                            leftAxis.setTextSize(16);
                                            leftAxis.setDrawAxisLine(false);

                                            String[] tempDates = new String[dates.size()];
                                            for(int i =0; i < tempDates.length; i++) {
                                                tempDates[i] = dates.get(i).substring(0, 6);
                                            }

                                            final String[] axisDates = tempDates;

                                            AxisValueFormatter formatter = new AxisValueFormatter()
                                            {
                                                @Override
                                                public String getFormattedValue(float value, AxisBase axis)
                                                {
                                                    if( value >= 0 && axisDates[(int)value] != null)
                                                    {
                                                        return axisDates[(int)value];
                                                    }
                                                    else
                                                    {
                                                        return "FAIL";
                                                    }
                                                }

                                                @Override
                                                public int getDecimalDigits()
                                                {
                                                    return 0;
                                                }
                                            };

                                            XAxis xAxis = chart.getXAxis();
                                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                            xAxis.setGranularity(1);
                                            xAxis.setValueFormatter(formatter);
                                            xAxis.setAxisMinValue(-0.6f);
                                            xAxis.setAxisMaxValue(dates.size() - 0.4f);
                                            xAxis.setTextSize(16f);
                                            xAxis.setDrawGridLines(false);

                                            chart.setDescription("");
                                            chart.getLegend().setEnabled(false);
                                            chart.setFitBars(true);
                                            chart.setExtraOffsets(0,16,0,16);
                                            chart.invalidate();
                                        }
                                    }
                                });
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Extras", Toast.LENGTH_SHORT).show();
        }

        Button winButton = findViewById(R.id.detail_win_button);
        Button loseButton = findViewById(R.id.detail_lose_button);

        winButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deckViewModel.insertMatch(deck.getId(), true);
            }
        });
        loseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deckViewModel.insertMatch(deck.getId(), false);
            }
        });
    }
}
