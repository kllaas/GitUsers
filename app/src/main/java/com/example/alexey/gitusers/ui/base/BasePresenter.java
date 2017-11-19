package com.example.alexey.gitusers.ui.base;

public interface BasePresenter<T> {

    void takeView(T view);

    void onDispose();

}
