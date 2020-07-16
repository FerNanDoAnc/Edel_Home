package com.example.fera1999.edelh.ui.change_password;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePasswordModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePasswordModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui va el cambio de clave2");
    }

    public LiveData<String> getText() {
        return mText;
    }
}