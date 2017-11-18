package com.example.alexey.gitusers.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.alexey.gitusers.App;
import com.example.alexey.gitusers.di.components.ActivityComponent;
import com.example.alexey.gitusers.di.components.DaggerActivityComponent;
import com.example.alexey.gitusers.di.modules.ActivityModule;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ActivityComponent component;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder()
                .applicationComponent(App.appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViews();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }

        super.onDestroy();
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public ActivityComponent getComponent() {
        return component;
    }

    protected abstract void setUpViews();
}
