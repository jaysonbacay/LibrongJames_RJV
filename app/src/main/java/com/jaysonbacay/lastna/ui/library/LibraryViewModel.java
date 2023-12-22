package com.jaysonbacay.lastna.ui.library;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
public class LibraryViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final MutableLiveData<String>mText;

    public LibraryViewModel (){
        mText=new MutableLiveData<>();
        mText.setValue("This is a dashboard fragment");
    }
    public LiveData<String>getText(){ return mText;}

}