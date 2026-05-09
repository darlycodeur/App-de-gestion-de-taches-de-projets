package com.taskflow.app.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.taskflow.app.R;

public class PlusBottomSheet extends BottomSheetDialogFragment {

    private OnOptionSelectedListener listener;

    public interface OnOptionSelectedListener {
        void onTachesSelected();

        void onCategoriesSelected();

        void onStatistiquesSelected();
    }

    public void setOnOptionSelectedListener(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_plus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.option_taches).setOnClickListener(v -> {
            if (listener != null) {
                listener.onTachesSelected();
            }
            dismiss();
        });

        view.findViewById(R.id.option_categories).setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoriesSelected();
            }
            dismiss();
        });

        view.findViewById(R.id.option_statistiques).setOnClickListener(v -> {
            if (listener != null) {
                listener.onStatistiquesSelected();
            }
            dismiss();
        });
    }
}
