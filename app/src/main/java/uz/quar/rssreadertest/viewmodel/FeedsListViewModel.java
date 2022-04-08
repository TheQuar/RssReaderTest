package uz.quar.rssreadertest.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.db.feeds.FeedsListRepo;
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.db.news.NewsListRepo;

public class FeedsListViewModel extends AndroidViewModel {
    private FeedsListRepo feedListRepo;
    public LiveData<List<FeedArticles>> getAllNews;

    public FeedsListViewModel(@NonNull Application application) {
        super(application);
        feedListRepo = new FeedsListRepo(application);
        getAllNews = feedListRepo.getAllFeeds();
    }

    public LiveData<List<FeedArticles>> getAllFeeds() {
        return getAllNews;
    }

    public void insert(List<FeedArticles> feedArticles) {
        feedListRepo.insert(feedArticles);
    }

    public void delete(FeedArticles feedArticles) {
        feedListRepo.delete(feedArticles);
    }

}
