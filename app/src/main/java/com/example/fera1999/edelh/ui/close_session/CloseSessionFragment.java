package com.example.fera1999.edelh.ui.close_session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fera1999.edelh.LoginActivity;
import com.example.fera1999.edelh.R;

import java.util.Objects;

public class CloseSessionFragment extends Fragment {

    private CloseSessionViewModel galleryViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor deleteData;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(CloseSessionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        deleteData = sharedPreferences.edit();
        deleteData.clear();
        deleteData.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        return root;
    }
}