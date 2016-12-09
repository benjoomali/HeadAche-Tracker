package com.benumali.android.headachetracker;

import android.support.v4.app.Fragment;

public class HeadacheListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new HeadacheListFragment();
    }

}
