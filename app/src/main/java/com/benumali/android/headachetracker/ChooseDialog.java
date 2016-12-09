package com.benumali.android.headachetracker;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.Date;
import java.util.UUID;

public class ChooseDialog extends DialogFragment{
    private static final String EXTRA_CHOOSE = "CHOOSE";

    public static ChooseDialog newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CHOOSE, date);
        ChooseDialog fragment = new ChooseDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.delete_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete shit
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel the dialog
                    }
                });
                return builder.create();
    }

}
