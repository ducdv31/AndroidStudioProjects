package com.example.myhouse.homecontent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.example.myhouse.R;
import com.example.myhouse.datahistory.THValue;
import com.example.myhouse.timeconverter.TimeConverter;
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

public class FragmentAH10 extends Fragment {
    private ArcGauge Temp;
    private ArcGauge Humi;
    private LineChart lineChart;
    private DatabaseReference databaseReference;

    public FragmentAH10() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ah10View = inflater.inflate(R.layout.fragment_a_h10, container, false);
        lineChart = ah10View.findViewById(R.id.chartAh10);
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
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Range Humidity = new Range();
        Humidity.setColor(Color.GREEN);
        Humidity.setFrom(0);
        Humidity.setTo(100);
        Range Temperature = new Range();
        Temperature.setColor(Color.RED);
        Temperature.setFrom(0);
        Temperature.setTo(100);

        Temp = (ArcGauge) ah10View.findViewById(R.id.temperature_ah10);
        Temp.setValueColor(Color.BLACK);
        Temp.addRange(Temperature);
        Humi = (ArcGauge) ah10View.findViewById(R.id.humidity_ah10);
        Humi.setValueColor(Color.BLACK);
        Humi.addRange(Humidity);

        return ah10View;
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
        databaseReference.child("AHT10/Current")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            THValue thValue = snapshot.getValue(THValue.class);
                            assert thValue != null;
                            Temp.setValue(thValue.getT());
                            Humi.setValue(thValue.getH());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // For chart
    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        databaseReference.child("AHT10/History")
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

    private void show2Chart(ArrayList<Entry> dataVals, String ChartName, int color, ArrayList<Entry> dataVals2, String ChartName2, int color2) {
        final LineDataSet lineDataSet = new LineDataSet(null, null);
        final LineDataSet lineDataSet2 = new LineDataSet(null, null);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();

        lineDataSet.setValues(dataVals);
        lineDataSet.setLabel(ChartName);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(4);
        lineDataSet.setDrawValues(false);

        lineDataSet2.setValues(dataVals2);
        lineDataSet2.setLabel(ChartName2);
        lineDataSet2.setColor(color2);
        lineDataSet2.setLineWidth(4);
        lineDataSet2.setDrawValues(false);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        iLineDataSets.add(lineDataSet2);
        LineData lineData = new LineData(iLineDataSets);

        lineChart.clear();
        lineChart.setData(lineData); // Draw
        lineChart.invalidate();
    }
}