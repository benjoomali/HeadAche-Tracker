package com.benumali.android.headachetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

public class HeadacheFragment extends Fragment{

    private static final String ARG_HEADACHE_ID = "headache_id";
    private static final String ARG_DESCRIPTION = "Description";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

    private Headache mHeadache;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mInoutCheckBox;
    //private Button mDeleteButton;
    private EditText mDescriptionField;
    private SeekBar mSeekBar;

    public static HeadacheFragment newInstance(UUID headacheId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_HEADACHE_ID, headacheId);

        HeadacheFragment fragment = new HeadacheFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID headacheId = (UUID) getArguments().getSerializable(ARG_HEADACHE_ID);
        mHeadache = HeadacheList.get(getActivity()).getHeadache(headacheId);
    }

    @Override
    public void onPause() {
        super.onPause();

        HeadacheList.get(getActivity())
                .updateHeadache(mHeadache);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_headache, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_headache:
                UUID headacheId = mHeadache.getId();
                HeadacheList.get(getActivity()).deleteHeadache(headacheId);

                Toast.makeText(getActivity(), R.string.deleted_menu, Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_headache, container, false);

        mTitleField = (EditText)v.findViewById(R.id.headache_title);
        mTitleField.setText(mHeadache.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if(mTitleField.getText().length()==0){
                  //  mTitleField.setError("Field can't be left blank.");
                //}
                mHeadache.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        });

        mDescriptionField = (EditText)v.findViewById(R.id.headache_description_text);
        mDescriptionField.setText(mHeadache.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHeadache.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        });

        mDateButton = (Button)v.findViewById(R.id.headache_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mHeadache.getDate());
                dialog.setTargetFragment(HeadacheFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.headache_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mHeadache.getTime());
                dialog.setTargetFragment(HeadacheFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });


        mInoutCheckBox = (CheckBox)v.findViewById(R.id.headache_inout);
        mInoutCheckBox.setChecked(mHeadache.isInout());
        mInoutCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mHeadache.setInout(isChecked);
            }
        });

        mSeekBar = (SeekBar)v.findViewById(R.id.headache_intensity_seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 5;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //
            }
        });

        /*
        mDeleteButton = (Button) v.findViewById(R.id.headache_delete);
        mDeleteButton.setEnabled(false);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                ChooseDialog dialog = new ChooseDialog();
                dialog.show(getFragmentManager(), "ChooseDialog");
            }


        });*/

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mHeadache.setDate(date);
            updateDate();
        }
        else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mHeadache.setTime(date);
            updateTime();
        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.format("EEEE, MMM dd, yyyy", mHeadache.getDate()).toString());
    }

    private void updateTime() {
        mTimeButton.setText(DateFormat.format("h:mm a", mHeadache.getTime()));
    }
}
