package com.example.fera1999.edelh.ui.habitaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.fera1999.edelh.R;

public class HabitacionesFragment extends Fragment {

    private HabitacionesViewModel habitacionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        habitacionesViewModel =
                ViewModelProviders.of(this).get(HabitacionesViewModel.class);
        View root = inflater.inflate(R.layout.habitaciones, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        habitacionesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
