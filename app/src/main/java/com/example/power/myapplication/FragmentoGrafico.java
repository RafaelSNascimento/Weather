package com.example.power.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class FragmentoGrafico extends Fragment {
    View view;
    static ArrayList<Entry> max = new ArrayList<>();
    static ArrayList<Entry> min = new ArrayList<>();
    static String[] max1 = new String[10];
    static String[] min1 = new String[10];
    static String[] label= new String[10];
    static ArrayList<String> labels = new ArrayList<>();
    static double x = 0 ;

    public FragmentoGrafico() {
        setRetainInstance(true);
    }

    static HomeActivity homeActivity;
    static LineChart lineChart;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_grafico, container, false);
        lineChart = (LineChart)view.findViewById(R.id.lineChart);
        homeActivity = (HomeActivity) getActivity();

        return view;
    }
    public static void Refresh(){
        max1 = homeActivity.Max;
        min1 = homeActivity.Min;
        label = homeActivity.Data
        ;
        String split[] = new String[2];
        labels.clear();
        max.clear();
        min.clear();
        for(int i = 0; i< 10; i++){
            split = max1[i].split(" ");
            max.add(new Entry(Float.parseFloat(split[1]),i));
            split = min1[i].split(" ");
            min.add(new Entry(Float.parseFloat(split[1]),i));
            labels.add(label[i]);
            x = x + 0.1;
        }

        LineDataSet lineDataSets1 = new LineDataSet(max, "Max.");
        lineDataSets1.setDrawCircles(false);
        lineDataSets1.setColor(Color.BLUE);

        LineDataSet lineDataSets2 = new LineDataSet(min, "Min.");
        lineDataSets1.setDrawCircles(false);
        lineDataSets1.setColor(Color.RED);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSets1);
        lineDataSets.add(lineDataSets2);
        lineChart.clear();
        lineChart.setData(new LineData(labels, lineDataSets));
    }
}
