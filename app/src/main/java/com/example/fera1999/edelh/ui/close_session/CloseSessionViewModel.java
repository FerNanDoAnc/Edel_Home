package com.example.fera1999.edelh.ui.close_session;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloseSessionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CloseSessionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui va la administracion de usuarios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}