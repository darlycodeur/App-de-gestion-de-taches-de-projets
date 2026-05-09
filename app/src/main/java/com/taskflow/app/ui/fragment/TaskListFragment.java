package com.taskflow.app.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.taskflow.app.R;
import com.taskflow.app.database.entity.Tache;
import com.taskflow.app.ui.activity.TaskDetailActivity;
import com.taskflow.app.ui.adapter.TaskDetailAdapter;
import com.taskflow.app.viewmodel.ProjectDetailViewModel;

public class TaskListFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "project_id";
    private ProjectDetailViewModel viewModel;
    private TaskDetailAdapter adapter;

    public static TaskListFragment newInstance(int projectId) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        int projectId = getArguments() != null ? getArguments().getInt(ARG_PROJECT_ID) : 0;
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectDetailViewModel.class);

        RecyclerView rvTasks = view.findViewById(R.id.rv_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskDetailAdapter();
        rvTasks.setAdapter(adapter);

        adapter.setOnTaskClickListener(task -> {
            Intent intent = new Intent(getContext(), TaskDetailActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            intent.putExtra("TASK_TITLE", task.getTitre());
            startActivity(intent);
        });

        adapter.setOnTaskLongClickListener(this::showTaskOptionsDialog);

        viewModel.getTasksByProject(projectId).observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) adapter.setTasks(tasks);
        });

        setupSwipe(rvTasks);

        return view;
    }

    private void setupSwipe(RecyclerView recyclerView) {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh,
                    @NonNull RecyclerView.ViewHolder t) { return false; }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Tache task = adapter.getTaskAt(position);
                adapter.notifyItemChanged(position);

                if (direction == ItemTouchHelper.LEFT) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Supprimer la tâche")
                            .setMessage("Voulez-vous vraiment supprimer \"" + task.getTitre() + "\" ? Cette action est irréversible.")
                            .setPositiveButton("Supprimer", (d, w) -> viewModel.deleteTask(task))
                            .setNegativeButton("Annuler", null)
                            .show();
                } else {
                    String[] statuts = {"à faire", "en cours", "terminée"};
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Changer le statut")
                            .setItems(statuts, (d, which) -> {
                                task.setStatut(statuts[which]);
                                viewModel.updateTask(task);
                            })
                            .setNegativeButton("Annuler", null)
                            .show();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void showTaskOptionsDialog(Tache task) {
        new AlertDialog.Builder(requireContext())
                .setTitle(task.getTitre())
                .setItems(new String[]{"Changer le statut", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) {
                        String[] statuts = {"à faire", "en cours", "terminée"};
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Changer le statut")
                                .setItems(statuts, (d, w) -> {
                                    task.setStatut(statuts[w]);
                                    viewModel.updateTask(task);
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Supprimer")
                                .setMessage("Voulez-vous vraiment supprimer \"" + task.getTitre() + "\" ?")
                                .setPositiveButton("Supprimer", (d, w) -> viewModel.deleteTask(task))
                                .setNegativeButton("Annuler", null)
                                .show();
                    }
                })
                .show();
    }
}
