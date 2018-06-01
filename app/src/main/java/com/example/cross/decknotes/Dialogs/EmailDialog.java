package com.example.cross.decknotes.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cross.decknotes.R;

public class EmailDialog extends BaseDialog
{
    public EmailDialog() {
        super(R.layout.email_dialog, R.string.email_title, R.string.email_positive, R.string.cancel );
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Spinner spinner = getDialog().findViewById(R.id.email_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                                                          new String[]{"I found a bug", "I want a new feature","I just wanna chat"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
