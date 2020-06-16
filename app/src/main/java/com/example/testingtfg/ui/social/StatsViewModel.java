package com.example.testingtfg.ui.social;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Aún no disponible");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
