package com.benumali.android.headachetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class HeadacheGraphActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new HeadacheGraphFragment();
    }
}
