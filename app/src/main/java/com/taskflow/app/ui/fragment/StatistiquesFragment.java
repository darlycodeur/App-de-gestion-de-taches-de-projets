package com.taskflow.app.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.taskflow.app.R;
import com.taskflow.app.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class StatistiquesFragment extends Fragment {

    private DashboardViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistiques, container, false);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setupPieChart(view);
        setupBarChart(view);

        return view;
    }

    private void setupPieChart(View view) {
        PieChart pieChart = view.findViewById(R.id.pie_chart_stats);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setCenterText("Tâches");
        pieChart.setCenterTextSize(14f);
        pieChart.setExtraOffsets(8f, 8f, 8f, 8f);

        viewModel.getTachesTodo().observe(getViewLifecycleOwner(), todo -> {
            Integer inProgress = viewModel.getTachesInProgress().getValue();
            Integer completed = viewModel.getTachesCompleted().getValue();
            updatePieChart(pieChart, todo, inProgress, completed);
        });
        viewModel.getTachesInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            Integer todo = viewModel.getTachesTodo().getValue();
            Integer completed = viewModel.getTachesCompleted().getValue();
            updatePieChart(pieChart, todo, inProgress, completed);
        });
        viewModel.getTachesCompleted().observe(getViewLifecycleOwner(), completed -> {
            Integer todo = viewModel.getTachesTodo().getValue();
            Integer inProgress = viewModel.getTachesInProgress().getValue();
            updatePieChart(pieChart, todo, inProgress, completed);
        });
    }

    private void updatePieChart(PieChart pieChart, Integer todo, Integer inProgress, Integer completed) {
        if (todo == null || inProgress == null || completed == null) return;
        List<PieEntry> entries = new ArrayList<>();
        if (todo > 0) entries.add(new PieEntry(todo, "À faire"));
        if (inProgress > 0) entries.add(new PieEntry(inProgress, "En cours"));
        if (completed > 0) entries.add(new PieEntry(completed, "Terminée"));
        if (entries.isEmpty()) return;

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
                Color.parseColor("#9E9E9E"),
                Color.parseColor("#1976D2"),
                Color.parseColor("#388E3C")
        );
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(2f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(data);
        pieChart.animateY(800, Easing.EaseInOutQuad);
        pieChart.invalidate();
    }

    private void setupBarChart(View view) {
        HorizontalBarChart barChart = view.findViewById(R.id.bar_chart_stats);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);
        barChart.setExtraOffsets(8f, 8f, 8f, 8f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#757575"));

        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setTextColor(Color.parseColor("#757575"));
        barChart.getAxisRight().setEnabled(false);

        viewModel.getEmployeeWorkload().observe(getViewLifecycleOwner(), employees -> {
            if (employees == null || employees.isEmpty()) return;

            List<BarEntry> entries = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            for (int i = 0; i < employees.size(); i++) {
                entries.add(new BarEntry(i, employees.get(i).tacheCount));
                labels.add(employees.get(i).employe.getNom());
            }

            BarDataSet dataSet = new BarDataSet(entries, "Tâches");
            dataSet.setColor(Color.parseColor("#1976D2"));
            dataSet.setValueTextSize(11f);
            dataSet.setValueTextColor(Color.parseColor("#212121"));

            BarData data = new BarData(dataSet);
            data.setBarWidth(0.6f);

            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());

            barChart.setData(data);
            barChart.animateY(800);
            barChart.invalidate();
        });
    }
}
