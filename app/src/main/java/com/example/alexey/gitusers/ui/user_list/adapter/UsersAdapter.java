package com.example.alexey.gitusers.ui.user_list.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alexey.gitusers.R;
import com.example.alexey.gitusers.data.entity.local.User;
import com.example.alexey.gitusers.ui.base.adapter.BaseRecyclerViewAdapter;
import com.example.alexey.gitusers.ui.base.adapter.BaseViewHolder;
import com.example.alexey.gitusers.ui.user_list.PaginationCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UsersAdapter extends BaseRecyclerViewAdapter<User, BaseViewHolder<User>> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    private boolean errorOccurred = false;

    private PaginationCallback callback;

    @Inject
    UsersAdapter(List<User> items) {
        super(items);
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
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        addItem(new User());
    }

    @Override
    public void remove(User item) {
        Log.d(TAG, "remove: " + item.getId());
        super.remove(item);
    }

    @Override
    public void addItem(User item) {
        Log.d(TAG, "addItem: " + item.getId());
        super.addItem(item);
    }

    public void removeLoadingFooter() {
        if (!isLoadingAdded) return;
        isLoadingAdded = false;

        User itemToRemove = getItem(items.size() - 1);
        remove(itemToRemove);
    }

    public void toggleRetry(boolean show) {
        errorOccurred = show;

        recyclerView.post(() -> notifyItemChanged(items.size() - 1));
    }

    public void setCallback(PaginationCallback callback) {
        this.callback = callback;
    }

    public boolean isErrorOccurred() {
        return errorOccurred;
    }

    public void setErrorOccurred(boolean errorOccurred) {
        this.errorOccurred = errorOccurred;
        isLoadingAdded = errorOccurred;
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
        protected void bind(User user) {
            login.setText(user.getLogin());

            if (user.getUrlToImage() != null)
                Glide.with(itemView.getContext())
                        .load(user.getUrlToImage())
                        .asBitmap()
                        .fitCenter()
                        .into(image);
        }
    }

    class LoadingViewHolder extends BaseViewHolder<User> {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        @BindView(R.id.try_again)
        Button tryAgain;

        @BindView(R.id.error_message)
        TextView errorMessage;

        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(User user) {
            toggleError(errorOccurred);
            tryAgain.setOnClickListener(view -> {
                callback.onRetryClick();
                toggleError(false);
            });
        }

        public void toggleError(boolean error) {
            errorMessage.setVisibility(error ? View.VISIBLE : View.GONE);
            tryAgain.setVisibility(error ? View.VISIBLE : View.GONE);
            progressBar.setVisibility(error ? View.GONE : View.VISIBLE);
        }
    }

}
