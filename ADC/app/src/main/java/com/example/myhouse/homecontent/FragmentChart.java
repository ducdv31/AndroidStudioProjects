package com.example.myhouse.homecontent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myhouse.R;
import com.example.myhouse.datahistory.THValue;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentChart extends Fragment {
    private LineChart lineChart;


    public FragmentChart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ChartView = inflater.inflate(R.layout.fragment_chart, container, false);
        lineChart = ChartView.findViewById(R.id.lineChart);

        return ChartView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListData();
    }

    @Override
    public void onStop() {
        super.onStop();
        lineChart.invalidate();
    }

    // For chart
    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DHT11/History")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    ArrayList<Entry> dataVals1 = new ArrayList<Entry>();
                                    ArrayList<Entry> dataVals2 = new ArrayList<Entry>();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        THValue thValue = dataSnapshot1.getValue(THValue.class);
                                        assert thValue != null;
                                        dataVals1.add(new Entry(Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getKey())),
                                                thValue.getT()));
                                        dataVals2.add(new Entry(Integer.parseInt(dataSnapshot1.getKey()),
                                                thValue.getH()));
                                    }
                                    show2Chart(dataVals1, "Temp", Color.RED, dataVals2, "Humi", Color.GREEN);
                                } else {
                                    lineChart.clear();
                                    lineChart.invalidate();
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showChart(ArrayList<Entry> dataVals, String ChartName, int color) {
        final LineDataSet lineDataSet = new LineDataSet(null, null);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel(ChartName);
        lineDataSet.setColor(color);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);

        lineChart.clear();
        lineChart.setData(lineData); // Draw
        lineChart.invalidate();
    }

    private void show2Chart(ArrayList<Entry> dataVals, String ChartName, int color, ArrayList<Entry> dataVals2, String ChartName2, int color2) {
        final LineDataSet lineDataSet = new LineDataSet(null, null);
        final LineDataSet lineDataSet2 = new LineDataSet(null, null);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel(ChartName);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(4);

        lineDataSet2.setValues(dataVals2);
        lineDataSet2.setLabel(ChartName2);
        lineDataSet2.setColor(color2);
        lineDataSet2.setLineWidth(4);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        iLineDataSets.add(lineDataSet2);
        LineData lineData = new LineData(iLineDataSets);

        lineChart.clear();
        lineChart.setData(lineData); // Draw
        lineChart.invalidate();
    }
}