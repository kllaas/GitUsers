package com.example.alexey.gitusers.ui.user_list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alexey.gitusers.R;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.adapter.BaseRecyclerViewAdapter;
import com.example.alexey.gitusers.ui.base.adapter.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends BaseRecyclerViewAdapter<User, BaseViewHolder<User>> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;

    private Context context;

    @Inject
    public UsersAdapter(List<User> items, Context context) {
        super(items);
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_user, parent, false);
                viewHolder = new UserViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }

        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
//        addItem(new User());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
//
//        User itemToRemove = getItem(items.size() - 1);
//        remove(itemToRemove);
    }

    class UserViewHolder extends BaseViewHolder<User> {

        @BindView(R.id.image)
        CircleImageView image;

        @BindView(R.id.login)
        TextView login;

        UserViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(User artist) {
            login.setText(artist.getLogin());

            if (artist.getUrlToImage() != null)
                Glide.with(itemView.getContext())
                        .load(artist.getUrlToImage())
                        .asBitmap()
                        .fitCenter()
                        .into(image);
        }
    }

    class LoadingViewHolder extends BaseViewHolder<User> {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(User artist) {}
    }

}
