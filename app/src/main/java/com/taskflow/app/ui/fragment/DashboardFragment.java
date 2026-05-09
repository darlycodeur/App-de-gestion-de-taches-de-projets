package com.taskflow.app.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.taskflow.app.R;
import com.taskflow.app.database.model.ProjectProgress;
import com.taskflow.app.ui.adapter.ProjectProgressAdapter;
import com.taskflow.app.ui.adapter.RecentTasksAdapter;
import com.taskflow.app.ui.bottomsheet.PlusBottomSheet;
import com.taskflow.app.viewmodel.DashboardViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private PieChart pieChart;
    private HorizontalBarChart horizontalBarChart;
    private BarChart barChartPriority;
    private BarChart barChartProjects;
    private RecentTasksAdapter recentTasksAdapter;
    private ProjectProgressAdapter projectProgressAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        TextView tvDate = view.findViewById(R.id.tv_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.FRANCE);
        tvDate.setText(sdf.format(new Date()));

        setupStatCards(view);
        setupPieChart(view);
        setupHorizontalBarChart(view);
        setupBarChartPriority(view);
        setupBarChartProjects(view);
        setupRecentTasks(view);
        setupProjectsProgress(view);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> showPlusBottomSheet());

        return view;
    }

    private void showPlusBottomSheet() {
        PlusBottomSheet bottomSheet = new PlusBottomSheet();
        bottomSheet.setOnOptionSelectedListener(new PlusBottomSheet.OnOptionSelectedListener() {
            @Override
            public void onTachesSelected() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TachesFragment()).commit();
            }
            @Override
            public void onCategoriesSelected() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CategoriesFragment()).commit();
            }
            @Override
            public void onStatistiquesSelected() {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new StatistiquesFragment()).commit();
            }
        });
        bottomSheet.show(getParentFragmentManager(), "PlusBottomSheet");
    }

    private void setupStatCards(View view) {
        View cardTotalProjects = view.findViewById(R.id.card_total_projects);
        View cardTachesTodo = view.findViewById(R.id.card_taches_todo);
        View cardTachesInProgress = view.findViewById(R.id.card_taches_inprogress);
        View cardTachesCompleted = view.findViewById(R.id.card_taches_completed);
        View cardTachesOverdue = view.findViewById(R.id.card_taches_overdue);

        setStatCard(cardTotalProjects, "Projets", "#1976D2");
        setStatCard(cardTachesTodo, "À faire", "#9E9E9E");
        setStatCard(cardTachesInProgress, "En cours", "#1976D2");
        setStatCard(cardTachesCompleted, "Terminées", "#388E3C");
        setStatCard(cardTachesOverdue, "En retard", "#D32F2F");

        viewModel.getTotalProjects().observe(getViewLifecycleOwner(), count ->
                ((TextView) cardTotalProjects.findViewById(R.id.stat_number))
                        .setText(String.valueOf(count != null ? count : 0)));
        viewModel.getTachesTodo().observe(getViewLifecycleOwner(), count ->
                ((TextView) cardTachesTodo.findViewById(R.id.stat_number))
                        .setText(String.valueOf(count != null ? count : 0)));
        viewModel.getTachesInProgress().observe(getViewLifecycleOwner(), count ->
                ((TextView) cardTachesInProgress.findViewById(R.id.stat_number))
                        .setText(String.valueOf(count != null ? count : 0)));
        viewModel.getTachesCompleted().observe(getViewLifecycleOwner(), count ->
                ((TextView) cardTachesCompleted.findViewById(R.id.stat_number))
                        .setText(String.valueOf(count != null ? count : 0)));
        viewModel.getTachesOverdue().observe(getViewLifecycleOwner(), count ->
                ((TextView) cardTachesOverdue.findViewById(R.id.stat_number))
                        .setText(String.valueOf(count != null ? count : 0)));
    }

    private void setStatCard(View card, String label, String colorHex) {
        ((TextView) card.findViewById(R.id.stat_label)).setText(label);
        TextView tvNumber = card.findViewById(R.id.stat_number);
        tvNumber.setTextColor(Color.parseColor(colorHex));
    }

    private void setupPieChart(View view) {
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(48f);
        pieChart.setTransparentCircleRadius(52f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setCenterText("Tâches");
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.parseColor("#212121"));
        pieChart.getLegend().setEnabled(true);
        pieChart.getLegend().setTextSize(13f);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setExtraOffsets(20f, 20f, 20f, 20f);
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        viewModel.getTachesTodo().observe(getViewLifecycleOwner(), todo -> updatePieChart());
        viewModel.getTachesInProgress().observe(getViewLifecycleOwner(), ip -> updatePieChart());
        viewModel.getTachesCompleted().observe(getViewLifecycleOwner(), done -> updatePieChart());
    }

    private void updatePieChart() {
        Integer todo = viewModel.getTachesTodo().getValue();
        Integer inProgress = viewModel.getTachesInProgress().getValue();
        Integer completed = viewModel.getTachesCompleted().getValue();
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
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(8f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(data);
        pieChart.animateY(1000, Easing.EaseInOutQuad);
        pieChart.invalidate();
    }

    private void setupHorizontalBarChart(View view) {
        horizontalBarChart = view.findViewById(R.id.horizontal_bar_chart);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.setDrawValueAboveBar(true);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.setExtraOffsets(8f, 8f, 24f, 8f);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#757575"));
        xAxis.setTextSize(11f);

        horizontalBarChart.getAxisLeft().setAxisMinimum(0f);
        horizontalBarChart.getAxisLeft().setTextColor(Color.parseColor("#757575"));
        horizontalBarChart.getAxisRight().setEnabled(false);

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
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override public String getFormattedValue(float value) {
                    return String.valueOf((int) value);
                }
            });

            BarData data = new BarData(dataSet);
            data.setBarWidth(0.6f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());
            horizontalBarChart.setData(data);
            horizontalBarChart.animateY(800);
            horizontalBarChart.invalidate();
        });
    }

    private void setupBarChartPriority(View view) {
        barChartPriority = view.findViewById(R.id.bar_chart_priority);
        barChartPriority.getDescription().setEnabled(false);
        barChartPriority.getLegend().setEnabled(false);
        barChartPriority.setDrawValueAboveBar(true);
        barChartPriority.setExtraOffsets(8f, 8f, 8f, 8f);

        XAxis xAxis = barChartPriority.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#757575"));
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(
                new String[]{"P1", "P2", "P3", "P4", "P5"}));

        barChartPriority.getAxisLeft().setAxisMinimum(0f);
        barChartPriority.getAxisLeft().setTextColor(Color.parseColor("#757575"));
        barChartPriority.getAxisRight().setEnabled(false);

        viewModel.getRecentTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks == null) return;
            int[] counts = new int[5];
            for (com.taskflow.app.database.model.TaskWithProject t : tasks) {
                int p = t.tache.getPriorite();
                if (p >= 1 && p <= 5) counts[p - 1]++;
            }
            List<BarEntry> entries = new ArrayList<>();
            int[] colors = {
                    Color.parseColor("#757575"),
                    Color.parseColor("#388E3C"),
                    Color.parseColor("#F9A825"),
                    Color.parseColor("#F57C00"),
                    Color.parseColor("#D32F2F")
            };
            List<Integer> barColors = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                entries.add(new BarEntry(i, counts[i]));
                barColors.add(colors[i]);
            }
            BarDataSet dataSet = new BarDataSet(entries, "");
            dataSet.setColors(barColors);
            dataSet.setValueTextSize(11f);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override public String getFormattedValue(float value) {
                    return String.valueOf((int) value);
                }
            });
            BarData data = new BarData(dataSet);
            data.setBarWidth(0.6f);
            barChartPriority.setData(data);
            barChartPriority.animateY(800);
            barChartPriority.invalidate();
        });
    }

    private void setupBarChartProjects(View view) {
        barChartProjects = view.findViewById(R.id.bar_chart_projects);
        barChartProjects.getDescription().setEnabled(false);
        barChartProjects.getLegend().setEnabled(true);
        barChartProjects.getLegend().setTextSize(11f);
        barChartProjects.setDrawValueAboveBar(true);
        barChartProjects.setExtraOffsets(8f, 8f, 8f, 8f);

        XAxis xAxis = barChartProjects.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#757575"));
        xAxis.setGranularity(1f);

        barChartProjects.getAxisLeft().setAxisMinimum(0f);
        barChartProjects.getAxisLeft().setAxisMaximum(100f);
        barChartProjects.getAxisLeft().setTextColor(Color.parseColor("#757575"));
        barChartProjects.getAxisRight().setEnabled(false);

        viewModel.getProjectsProgress().observe(getViewLifecycleOwner(), projects -> {
            if (projects == null || projects.isEmpty()) return;
            List<BarEntry> entries = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            for (int i = 0; i < projects.size(); i++) {
                ProjectProgress pp = projects.get(i);
                float pct = pp.getTotalTasks() == 0 ? 0f
                        : (pp.getCompletedTasks() * 100f / pp.getTotalTasks());
                entries.add(new BarEntry(i, pct));
                // Truncate long names
                String name = pp.getProjectName();
                labels.add(name.length() > 10 ? name.substring(0, 10) + "…" : name);
            }
            BarDataSet dataSet = new BarDataSet(entries, "% Complétion");
            dataSet.setColor(Color.parseColor("#388E3C"));
            dataSet.setValueTextSize(11f);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override public String getFormattedValue(float value) {
                    return (int) value + "%";
                }
            });
            BarData data = new BarData(dataSet);
            data.setBarWidth(0.6f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelCount(labels.size());
            barChartProjects.setData(data);
            barChartProjects.animateY(800);
            barChartProjects.invalidate();
        });
    }

    private void setupRecentTasks(View view) {
        RecyclerView rvRecentTasks = view.findViewById(R.id.rv_recent_tasks);
        rvRecentTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        recentTasksAdapter = new RecentTasksAdapter();
        rvRecentTasks.setAdapter(recentTasksAdapter);

        recentTasksAdapter.setOnTaskClickListener(item -> {
            Intent intent = new Intent(getContext(),
                    com.taskflow.app.ui.activity.TaskDetailActivity.class);
            intent.putExtra("TASK_ID", item.tache.getId());
            intent.putExtra("TASK_TITLE", item.tache.getTitre());
            startActivity(intent);
        });

        viewModel.getRecentTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                List<com.taskflow.app.database.model.TaskWithProject> limited =
                        tasks.size() > 5 ? tasks.subList(0, 5) : tasks;
                recentTasksAdapter.setTasks(limited);
            }
        });
    }

    private void setupProjectsProgress(View view) {
        RecyclerView rvProjectsProgress = view.findViewById(R.id.rv_projects_progress);
        rvProjectsProgress.setLayoutManager(new LinearLayoutManager(getContext()));
        projectProgressAdapter = new ProjectProgressAdapter();
        rvProjectsProgress.setAdapter(projectProgressAdapter);

        projectProgressAdapter.setOnProjectClickListener(project -> {
            Intent intent = new Intent(getContext(),
                    com.taskflow.app.ui.activity.ProjectDetailActivity.class);
            intent.putExtra("PROJECT_ID", project.getProjectId());
            intent.putExtra("PROJECT_NAME", project.getProjectName());
            startActivity(intent);
        });

        viewModel.getProjectsProgress().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) projectProgressAdapter.setProjects(projects);
        });
    }
}
