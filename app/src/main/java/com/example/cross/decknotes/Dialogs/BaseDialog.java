package com.example.cross.decknotes.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public abstract class BaseDialog extends DialogFragment
{
    int layout;
    int title;
    int positiveButton;
    int negativeButton;

    DeckDialogListener listener;

    BaseDialog(int layout, int title, int positiveButton, int negativeButton) {
        this.layout = layout;
        this.title = title;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        if (activity instanceof DeckDialogListener) {
            listener = (DeckDialogListener)activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implement DeckDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(layout, null))
                .setTitle(title)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        listener.onDialogPositiveClick(BaseDialog.this);
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        listener.onDialogNegativeClick(BaseDialog.this);
                    }
                });
        return builder.create();
    }

    public interface DeckDialogListener
    {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }
}
