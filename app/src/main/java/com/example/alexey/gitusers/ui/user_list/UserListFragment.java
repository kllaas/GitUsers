package com.example.alexey.gitusers.ui.user_list;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexey.gitusers.R;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.di.components.FragmentComponent;
import com.example.alexey.gitusers.ui.base.BaseFragment;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListFragment extends BaseFragment implements UserListMvpContract.View {

    private static final String LIST_STATE_KEY = "list_state";
    private static final String LIST_DATA_KEY = "data_state";
    private static final String ERROR_LOADING_KEY = "error_state";
    private static final String LAST_ID_KEY = "last_id_state";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    UserListMvpContract.Presenter<UserListMvpContract.View> presenter;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        FragmentComponent component = getComponent();
        component.inject(this);

        setUnBinder(ButterKnife.bind(this, view));

        presenter.takeView(this);
        setUpViews();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDispose();
    }

    @Override
    protected void setUpViews() {
        toolbar.setTitle(getString(R.string.app_name));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(presenter.getLayoutManager());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(presenter.getOnScrollListener());

        presenter.getAdapter().setRecyclerView(recyclerView);
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            presenter.refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDispose();
    }

    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);

        Parcelable listState = presenter.getLayoutManager().onSaveInstanceState();
        Serializable adapterData = (Serializable) presenter.getAdapter().getAll();
        Serializable error = presenter.getAdapter().isErrorOccurred();
        Serializable lastId = presenter.getLastId();

        state.putParcelable(LIST_STATE_KEY, listState);
        state.putSerializable(LIST_DATA_KEY, adapterData);
        state.putSerializable(ERROR_LOADING_KEY, error);
        state.putSerializable(LAST_ID_KEY, lastId);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle state) {
        super.onViewStateRestored(state);

        if (state == null) return;

        Parcelable listState = state.getParcelable(LIST_STATE_KEY);
        List<User> adapterData = (List<User>) state.getSerializable(LIST_DATA_KEY);
        boolean errorOccurred = (boolean) state.getSerializable(ERROR_LOADING_KEY);
        long lastId = (long) state.getSerializable(LAST_ID_KEY);

        presenter.getAdapter().updateDataSet(adapterData);
        presenter.getLayoutManager().onRestoreInstanceState(listState);
        presenter.getAdapter().setErrorOccurred(errorOccurred);
        presenter.setLastId(lastId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.albums_menu, menu);
    }

}
