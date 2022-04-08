package uz.quar.rssreadertest.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.quar.rssreadertest.R;
import uz.quar.rssreadertest.adapter.ArticleListAdapter;
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.ui.activity.ActivityDetails;
import uz.quar.rssreadertest.utils.MyDialog;
import uz.quar.rssreadertest.utils.NetworkUtil;
import uz.quar.rssreadertest.viewmodel.NewsListViewModel;


public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ArticleListAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ArticleListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsListViewModel viewModel;

    public FragmentNews() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewsListViewModel.class);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.purple_500, R.color.purple_500);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(this);

        viewModel.getAllNews().observe(getViewLifecycleOwner(), feedArticles -> {
            if (feedArticles != null) {
                mAdapter = new ArticleListAdapter(feedArticles);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setOnItemClickListener(this);
            }
        });

        if (!NetworkUtil.isNetworkAvailable(getContext())) {
            MyDialog.show(getContext(), "Network Error", "Please check your internet connection");
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            viewModel.refresh();
        }
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        viewModel.refresh();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(NewsArticles article) {
        Intent i = new Intent(getContext(), ActivityDetails.class);
        i.putExtra("data", article);
        startActivity(i);
    }
}