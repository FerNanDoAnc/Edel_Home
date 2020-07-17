package com.example.fera1999.edelh.ui.users;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UsersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui va la administracion de usuarios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}