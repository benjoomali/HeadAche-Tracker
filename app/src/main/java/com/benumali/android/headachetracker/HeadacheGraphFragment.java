package com.benumali.android.headachetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.UUID;

public class HeadacheGraphFragment extends Fragment{
    private static final String EXTRA_HEADACHE_GRAPH_ID = "com.benumali.android.HeadacheTracker.headache_graph_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.graph_view_time, container, false);

        GraphView graph = (GraphView) v.findViewById(R.id.graph_time);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(
                new DataPoint[] {
                        new DataPoint(8, 1),
                        new DataPoint(9, 5),
                        new DataPoint(10, 3),
                        new DataPoint(11, 2),
                        new DataPoint(12, 6)
                });
        graph.setTitle("Graph of Headaches over the Course of a Day");
        String horizontalLabel = "Time of Day";
        String verticalLabel = "# of Headaches";
        graph.getGridLabelRenderer().setHorizontalAxisTitle(horizontalLabel);
        graph.getGridLabelRenderer().setVerticalAxisTitle(verticalLabel);



        graph.addSeries(series);

        return v;
    }

}
