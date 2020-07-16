package com.example.fera1999.edelh.ui.switches;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwitchesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SwitchesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí va la administracion de switches");
    }

    public LiveData<String> getText() {
        return mText;
    }
}