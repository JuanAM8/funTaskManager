package com.example.testingtfg.ui.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TasksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(null);
    }

    public LiveData<String> getText() {
        return mText;
    }
}