package com.example.alexey.gitusers.ui.user_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.alexey.gitusers.R;
import com.example.alexey.gitusers.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class UserListActivity extends BaseActivity {

    private static final String TAG_USER_LIST_FRAGMENT = UserListFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setUnbinder(ButterKnife.bind(this));

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_USER_LIST_FRAGMENT);

        if (fragment == null) {
            fragment = UserListFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.fragments_container, fragment, TAG_USER_LIST_FRAGMENT)
                    .commit();
        }
    }

}
