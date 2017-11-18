package com.example.alexey.gitusers.ui.user_list;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alexey.gitusers.R;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.BaseActivity;
import com.example.alexey.gitusers.ui.user_list.adapter.UsersAdapter;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListActivity extends BaseActivity implements UserListMvpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.error_container)
    ViewGroup errorContainer;

    @BindView(R.id.error_message)
    TextView errorMessage;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    UserListMvpContract.Presenter<UserListMvpContract.View> presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        setUnbinder(ButterKnife.bind(this));

        getComponent().inject(this);
    }

    @Override
    protected void setUpViews() {
        presenter.takeView(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.app_name));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(presenter.getAdapter());
        recyclerView.addOnScrollListener(presenter.getOnScrollListener());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFailureMessage(String message) {
        errorContainer.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

}
