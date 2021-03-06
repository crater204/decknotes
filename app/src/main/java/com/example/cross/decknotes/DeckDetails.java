package com.example.cross.decknotes;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cross.decknotes.DataBase.Entities.DeckEntity;
import com.example.cross.decknotes.DataBase.Entities.RecordEntity;
import com.example.cross.decknotes.DataBase.ViewModel.DeckViewModel;
import com.example.cross.decknotes.Dialogs.BaseDialog;
import com.example.cross.decknotes.Dialogs.ConfirmDeleteDialog;
import com.example.cross.decknotes.Dialogs.EditDeckNameDialog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeckDetails extends AppCompatActivity implements BaseDialog.DeckDialogListener
{
    private DeckEntity deck;
    private DeckViewModel deckViewModel;
    private final String EDIT_DIALOG_TAG = "EditDeckDialog";
    private final String CONFIRM_DELETE_DIALOG_TAG = "ConfirmDeleteDialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId()) {
            case R.id.action_edit:
                EditDeckNameDialog editDialog = new EditDeckNameDialog();
                editDialog.show(getFragmentManager(), EDIT_DIALOG_TAG);
                return true;
            case R.id.action_delete:
                ConfirmDeleteDialog deleteDialog = new ConfirmDeleteDialog();
                deleteDialog.show(getFragmentManager(), CONFIRM_DELETE_DIALOG_TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_details);

        if(getIntent().hasExtra("DeckId"))
        {
            int id = (int)getIntent().getExtras().get("DeckId");

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
                                        setupBarGraph(recordEntities);
                                    }
                                });
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: No Extras", Toast.LENGTH_SHORT).show();
        }

        Button winButton = findViewById(R.id.detail_win_button);
        Button loseButton = findViewById(R.id.detail_lose_button);

        winButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addMatch(true);
            }
        });
        loseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addMatch(false);
            }
        });
    }

    private void addMatch(boolean isWin) {
        final RecordEntity record = deckViewModel.insertMatch(deck.getId(), isWin);

        String message;
        if(isWin) {
            message = "Win added";
        } else {
            message = "Loss added";
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.detail_container), message, Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deckViewModel.deleteRecord(record);
            }
        });
        snackbar.show();
    }

    private void setupBarGraph(@Nullable List<RecordEntity> recordEntities)
    {
        if(recordEntities != null)
        {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd yyyy", Locale.US);
            ArrayList<String> dates = new ArrayList<>();

            for(int i = 0; i < recordEntities.size(); i++)
            {
                String formattedDate = dateFormatter.format(recordEntities.get(i).getDate());
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
                for(int recordIndex = 0; recordIndex < recordEntities.size(); recordIndex++)
                {
                    if(dateFormatter.format(recordEntities.get(recordIndex).getDate()).equals(dates.get(dateIndex)))
                    {
                        if(recordEntities.get(recordIndex).isWin())
                        {
                            winCount++;
                        }
                        playCount++;
                    }
                }
                float percentage = ((float)winCount / (float)playCount * 100);
                int value = Math.round(percentage);
                barEntries.add(new BarEntry(dateIndex, value));
            }

            BarDataSet dataSet = new BarDataSet(barEntries, "Win Percentages");
            dataSet.setHighlightEnabled(false);
            dataSet.setValueFormatter(new ValueFormatter()
            {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                                ViewPortHandler viewPortHandler)
                {
                    return String.valueOf((int) value + "%");
                }
            });
            BarData data = new BarData(dataSet);
            data.setBarWidth(0.9f);
            data.setValueTextSize(16);
            BarChart chart = findViewById(R.id.bar_chart);
            chart.setData(data);
            chart.setScaleEnabled(false);
            chart.setDoubleTapToZoomEnabled(false);
            chart.setPinchZoom(false);

            YAxis rightAxis = chart.getAxisRight();
            rightAxis.setEnabled(false);

            YAxis leftAxis = chart.getAxisLeft();
            leftAxis.setAxisMaxValue(100);
            leftAxis.setAxisMinValue(0);
            leftAxis.setTextSize(16);
            leftAxis.setDrawAxisLine(false);

            String[] tempDates = new String[dates.size()];
            for(int i = 0; i < tempDates.length; i++)
            {
                tempDates[i] = dates.get(i).substring(0, 6);
            }

            final String[] axisDates = tempDates;

            AxisValueFormatter formatter = new AxisValueFormatter()
            {
                @Override
                public String getFormattedValue(float value, AxisBase axis)
                {
                    int formattedValue = (int)value;
                    if(formattedValue >= 0 && formattedValue < axisDates.length && axisDates[formattedValue] != null)
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
            chart.setExtraOffsets(0, 16, 0, 16);
            chart.invalidate();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog)
    {
        switch(dialog.getTag()) {
            case EDIT_DIALOG_TAG:
                EditText deckNameEditText = dialog.getDialog().findViewById(R.id.deck_name_edit_text);
                String newName = deckNameEditText.getText().toString();
                deckViewModel.editDeckName(deck, newName);
                break;
            case CONFIRM_DELETE_DIALOG_TAG:
                deckViewModel.deleteDeck(deck);
                Intent i = new Intent(this, DeckSelector.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            default:
                throw new Error("No dialog was handled");
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog)
    {
        // nothing need to be done when the user hits cancel
    }
}
