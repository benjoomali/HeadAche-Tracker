package com.benumali.android.headachetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HeadacheListFragment extends Fragment{

    private RecyclerView mHeadacheRecyclerView;
    private HeadacheAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headache_list, container, false);
        mHeadacheRecyclerView = (RecyclerView) view.findViewById(R.id.headache_recycler_view);
        mHeadacheRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_headache_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_headache:
                Headache headache = new Headache();
                HeadacheList.get(getActivity()).addHeadache(headache);
                Intent intent = HeadachePagerActivity.newIntent(getActivity(), headache.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_graph:
                Intent i = new Intent(getActivity(), HeadacheGraphActivity.class);
                startActivity(i);
                //null
                return true;
                //Intent i = HeadacheGraphFragment.newIntent(getActivity(), headache.getId());
                //startActivity(i);
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        HeadacheList headacheList = HeadacheList.get(getActivity());
        int headacheCount = headacheList.getHeadaches().size();

        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, headacheCount, headacheCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        HeadacheList headacheList = HeadacheList.get(getActivity());
        List<Headache> headaches = headacheList.getHeadaches();

        if (mAdapter == null) {
            mAdapter = new HeadacheAdapter(headaches);
            mHeadacheRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setHeadaches(headaches);
            mAdapter.notifyDataSetChanged();
        }

        //if (headaches.size() > 0) {
            //
        //} else {
            //
        //}

        updateSubtitle();
    }

    private class HeadacheHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private Headache mHeadache;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mTimeTextView;
        private CheckBox mInoutCheckBox;

        public HeadacheHolder(View itemView) {
            super(itemView);
                itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_headache_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_headache_date_text_view);
            mTimeTextView = (TextView) itemView.findViewById(R.id.list_item_headache_time_text_view);
            mInoutCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_headache_inout_check_box);
        }

        public void bindHeadache(Headache headache) {
            mHeadache = headache;
            mTitleTextView.setText(mHeadache.getTitle());
            mDateTextView.setText(DateFormat.format("EEEE, MMM dd, yyyy", mHeadache.getDate()).toString());

            mTimeTextView.setText(DateFormat.format("h:mm a", mHeadache.getTime()));
            mInoutCheckBox.setChecked(mHeadache.isInout());
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getActivity(), mHeadache.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            Intent intent = HeadachePagerActivity.newIntent(getActivity(), mHeadache.getId());
            startActivity(intent);
        }
    }


    private class HeadacheAdapter extends RecyclerView.Adapter<HeadacheHolder> {
        private List<Headache> mHeadaches;
        public HeadacheAdapter(List<Headache> headaches) {
            mHeadaches = headaches;
        }

        @Override
        public HeadacheHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_headache, parent, false);
            return new HeadacheHolder(view);
        }
        @Override
        public void onBindViewHolder(HeadacheHolder holder, int position) {
            Headache headache = mHeadaches.get(position);
            holder.bindHeadache(headache);
        }
        @Override
        public int getItemCount() {
            return mHeadaches.size();
        }

        public void setHeadaches(List<Headache> headaches) {
            mHeadaches = headaches;
        }
    }
}
