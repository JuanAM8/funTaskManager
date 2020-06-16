package com.example.testingtfg.ui.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskEditorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TaskEditorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tasks editor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}