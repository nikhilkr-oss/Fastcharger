package com.example.fastcharger.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.fastcharger.MainActivity;
import com.example.fastcharger.R;
import com.example.fastcharger.Utilty.SharedPrefUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private TextView[] dots;
    //SharedPrefUtils sharedPrefUtils=new SharedPrefUtils();
    Dialog dialog;
    ImageView navmenu;
    PieChart pieChart;
    LineChart lineChart;
    private int[] layouts;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        init();





        navmenu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();

            }
        });


        // adding bottom dots


        return view;
    }

    private void init() {
        navmenu = view.findViewById(R.id.navmenu);

        SharedPrefUtils.saveToPrefs(getActivity(), "test", 550);
        lineChart = view.findViewById(R.id.line_chart);
        LineChartinit();
        pieChart = view.findViewById(R.id.piechart);
        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);




        PieChartinit(mPieChart);
    }

    private void PieChartinit(PieChart mPieChart) {
        mPieChart.addPieSlice(new PieModel("", 50, Color.parseColor("#00B7D1")));
        mPieChart.addPieSlice(new PieModel("", 25, Color.parseColor("#FF3E3E")));
        mPieChart.addPieSlice(new PieModel("", 10, Color.parseColor("#11E26B")));
        mPieChart.getHighlightStrength();
        mPieChart.setInnerPadding(45);
        mPieChart.setHighlightStrength(10);
//mPieChart.setInnerPaddingOutline(22);
        mPieChart.startAnimation();
    }

    private void LineChartinit() {
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        ArrayList<Entry> yvalues = new ArrayList<>();
        yvalues.add(new Entry(0, 50f));
        yvalues.add(new Entry(4, 30f));
        yvalues.add(new Entry(8, 20f));
        yvalues.add(new Entry(12, 60f));
        yvalues.add(new Entry(16, 80f));
        yvalues.add(new Entry(20, 100f));

        LineDataSet set1 = new LineDataSet(yvalues, "percentage");
        set1.setDrawHighlightIndicators(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.enableAxisLineDashedLine(15, 8, 0);
//        xAxis.enableGridDashedLine(15, 8, 0);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(R.color.grey);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        lineChart.getDescription().setEnabled(false);

        YAxis rightAxis = lineChart.getAxisRight();
        lineChart.getXAxis().enableAxisLineDashedLine(15, 8, 0);
        lineChart.getXAxis().enableGridDashedLine(15, 8, 0);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setDrawAxisLine(false);
lineChart.setDrawGridBackground(false);

        set1.enableDashedLine(15, 8, 0);
        set1.setFillColor(R.color.color_primary);
        set1.setDrawFilled(true);
        set1.setDrawVerticalHighlightIndicator(false);
        set1.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.gradient));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        lineChart.setDrawBorders(false);

        lineChart.setDrawGridBackground(false);

        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh
    }

    ;

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}