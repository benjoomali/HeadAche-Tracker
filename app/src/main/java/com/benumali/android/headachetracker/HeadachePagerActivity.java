package com.benumali.android.headachetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class HeadachePagerActivity extends AppCompatActivity {
    private static final String EXTRA_HEADACHE_ID = "com.benumali.android.HeadacheTracker.headache_id";


    private ViewPager mViewPager;
    private List<Headache> mHeadaches;

    public static Intent newIntent(Context packageContext, UUID headacheId) {
        Intent intent = new Intent(packageContext, HeadachePagerActivity.class);
        intent.putExtra(EXTRA_HEADACHE_ID, headacheId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headache_pager);

        UUID headacheId = (UUID) getIntent().getSerializableExtra(EXTRA_HEADACHE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_headache_pager_view_pager);

        mHeadaches = HeadacheList.get(this).getHeadaches();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Headache headache = mHeadaches.get(position);
                return HeadacheFragment.newInstance(headache.getId());
            }

            @Override
            public int getCount() {
                return mHeadaches.size();
            }
        });

        for (int i = 0; i < mHeadaches.size(); i++) {
            if (mHeadaches.get(i).getId().equals(headacheId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
