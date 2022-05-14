package com.example.proiectmds.ui.budget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BudgetViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BudgetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This tab helps organize your budget");
    }
    // Load and use views afterwards
    public LiveData<String> getText() {
        return mText;
    }
}