package com.example.fera1999.edelh.ui.habitaciones;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class HabitacionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HabitacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Habitaciones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}