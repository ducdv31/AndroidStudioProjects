package com.example.myhouse.homecontent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.myhouse.R;
import com.example.myhouse.model.PmsModel;
import com.example.myhouse.timeconverter.TimeConverter2;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentPms7003 extends Fragment {

    private HalfGauge pm1;
    private HalfGauge pm25;
    private HalfGauge pm10;
    private LineChart lineChart;
    private DatabaseReference databaseReference;

    public FragmentPms7003() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View pms7003View = inflater
                .inflate(R.layout.fragment_pms7003, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        pm1 = pms7003View.findViewById(R.id.pm1);
        pm25 = pms7003View.findViewById(R.id.pm25);
        pm10 = pms7003View.findViewById(R.id.pm10);
        lineChart = pms7003View.findViewById(R.id.chartPms7003);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(true);
        lineChart.getXAxis().enableGridDashedLine(10f, 10f, 0f);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(true);
        lineChart.getAxisLeft().enableGridDashedLine(10f, 10f, 0f);
        lineChart.getXAxis().setAxisLineWidth(1f);
        lineChart.getAxisLeft().setAxisLineWidth(1f);

        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return TimeConverter2.convertFromMinutes((int) value);
            }
        });

        pm1.setMaxValue(100);
        pm25.setMaxValue(100);
        pm10.setMaxValue(100);
        Range green = new Range();
        green.setColor(Color.GREEN);
        green.setFrom(0);
        green.setTo(25);

        Range yellow = new Range();
        yellow.setColor(Color.YELLOW);
        yellow.setFrom(25);
        yellow.setTo(50);

        Range red = new Range();
        red.setColor(Color.RED);
        red.setFrom(50);
        red.setTo(100);


        pm1.addRange(green);
        pm1.addRange(yellow);
        pm1.addRange(red);

        pm25.addRange(green);
        pm25.addRange(yellow);
        pm25.addRange(red);

        pm10.addRange(green);
        pm10.addRange(yellow);
        pm10.addRange(red);

        return pms7003View;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetCurrentData();
        GetListData();
    }

    @Override
    public void onResume() {
        super.onResume();
        GetCurrentData();
        GetListData();
    }

    @Override
    public void onStop() {
        super.onStop();
        lineChart.invalidate();
    }

    // For Gauge
    private void GetCurrentData() {    /* Key: { t: value1, h: value2 } */
        databaseReference.child("PMS7003/Current")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            PmsModel pmsModel = snapshot.getValue(PmsModel.class);
                            assert pmsModel != null;
                            pm1.setValue(pmsModel.getPm1());
                            pm25.setValue(pmsModel.getPm25());
                            pm10.setValue(pmsModel.getPm10());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // For chart
    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        databaseReference.child("PMS7003/History")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    ArrayList<Entry> dataVals1 = new ArrayList<Entry>();
                                    ArrayList<Entry> dataVals2 = new ArrayList<Entry>();
                                    ArrayList<Entry> dataVals3 = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        PmsModel pmsModel = dataSnapshot1.getValue(PmsModel.class);
                                        assert pmsModel != null;
                                        dataVals1.add(new Entry(Integer.parseInt(Objects.requireNonNull(dataSnapshot1.getKey())),
                                                pmsModel.getPm1()));
                                        dataVals2.add(new Entry(Integer.parseInt(dataSnapshot1.getKey()),
                                                pmsModel.getPm25()));
                                        dataVals3.add(new Entry(Integer.parseInt(dataSnapshot1.getKey()),
                                                pmsModel.getPm10()));
                                    }
                                    show3Chart(dataVals1, "PM 1.0", Color.RED,
                                            dataVals2, "PM 2.5", Color.YELLOW,
                                            dataVals3, "PM 10", Color.BLUE);
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

    private void show3Chart(ArrayList<Entry> dataVals, String ChartName, int color,
                            ArrayList<Entry> dataVals2, String ChartName2, int color2,
                            ArrayList<Entry> dataVals3, String ChartName3, int color3) {
        final LineDataSet lineDataSet = new LineDataSet(null, null);
        final LineDataSet lineDataSet2 = new LineDataSet(null, null);
        final LineDataSet lineDataSet3 = new LineDataSet(null, null);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        // Line 1
        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel(ChartName);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(4);
        lineDataSet.setDrawValues(false);

        // Line 2
        lineDataSet2.setValues(dataVals2);
        lineDataSet2.setLabel(ChartName2);
        lineDataSet2.setColor(color2);
        lineDataSet2.setLineWidth(4);
        lineDataSet2.setDrawValues(false);

        // Line 3
        lineDataSet3.setValues(dataVals3);
        lineDataSet3.setLabel(ChartName3);
        lineDataSet3.setColor(color3);
        lineDataSet3.setLineWidth(4);
        lineDataSet3.setDrawValues(false);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        iLineDataSets.add(lineDataSet2);
        iLineDataSets.add(lineDataSet3);
        LineData lineData = new LineData(iLineDataSets);

        lineChart.clear();
        lineChart.setData(lineData); // Draw
        lineChart.invalidate();
    }
}