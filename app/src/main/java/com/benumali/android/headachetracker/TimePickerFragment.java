package com.benumali.android.headachetracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.benumali.android.HeadacheTracker.time";
    private Date mTime;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, mTime);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mTime = (Date) getArguments().getSerializable(EXTRA_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int period = calendar.get(Calendar.AM_PM);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)view.findViewById(R.id.dialog_time_picker);
        if(Build.VERSION.SDK_INT>=23) {
            timePicker.getHour(/*hour*/);
            timePicker.getMinute(/*minutes*/);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minutes);
            timePicker.setIs24HourView(false);
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mTime.setHours(hourOfDay);
                mTime.setMinutes(minute);
                Log.d("TimePickerFragment", "hour: " + hourOfDay + "; minute: " + minute);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
