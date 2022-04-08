package uz.quar.rssreadertest.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.adapter.ArticleListAdapter;
import uz.quar.rssreadertest.adapter.FeedsListAdapter;
import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.viewmodel.FeedsListViewModel;
import uz.quar.rssreadertest.viewmodel.NewsListViewModel;

public class FragmentMyFeeds extends Fragment implements FeedsListAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private FeedsListAdapter mAdapter;
    private FeedsListViewModel viewModel;
    private NewsListViewModel newsListViewModel;

    public FragmentMyFeeds() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_my_feeds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FeedsListViewModel.class);
        newsListViewModel = new ViewModelProvider(this).get(NewsListViewModel.class);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        viewModel.getAllNews.observe(getViewLifecycleOwner(), feedArticles -> {
            if (feedArticles != null) {
                mAdapter = new FeedsListAdapter(feedArticles);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(this);
            }
        });
    }

    @Override
    public void onItemClick(FeedArticles article) {
        showDialog(article);
    }

    private void showDialog(FeedArticles article) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(article.getTitle());
        builder.setMessage("Are you sure you want to delete this feed?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            newsListViewModel.deleteAll(article.getFeedUrl());
            viewModel.delete(article);
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();
    }
}