package com.example.mvvmjava.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.mvvmjava.BR;

public class UserViewModel extends BaseObservable {

    private String name;
    private int num = 0;

    public ObservableField<String> observableField = new ObservableField<>();

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        notifyPropertyChanged(BR.num);
    }

    public void onClickUp() {
        this.num = num++;
        observableField.set("Number : " + num);
        notifyPropertyChanged(BR.num);
    }

}
