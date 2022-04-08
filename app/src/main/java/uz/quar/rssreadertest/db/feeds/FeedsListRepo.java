package uz.quar.rssreadertest.db.feeds;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uz.quar.rssreadertest.db.AppDatabase;
import uz.quar.rssreadertest.db.news.NewsArticlesDao;

public class FeedsListRepo {
    public FeedListDao feedListDao;
    private AppDatabase appDatabase;

    public LiveData<List<FeedArticles>> getFeedsList;

    public FeedsListRepo(Application application) {
        appDatabase = AppDatabase.getInstance(application);
        feedListDao = appDatabase.feedListDao();
        getFeedsList = feedListDao.getAllFeeds();
    }

    public LiveData<List<FeedArticles>> getAllFeeds() {
        return getFeedsList;
    }

    public void insert(List<FeedArticles> feedArticles) {
        new InsertAsyncTask(feedListDao).execute(feedArticles);
    }

    public void delete(FeedArticles feedArticles) {
        feedListDao.delete(feedArticles);
    }

    private class InsertAsyncTask extends AsyncTask<List<FeedArticles>, Void, Void> {
        FeedListDao feedListDao;

        public InsertAsyncTask(FeedListDao feedListDao) {
            this.feedListDao = feedListDao;
        }

        @Override
        protected Void doInBackground(List<FeedArticles>... lists) {
            feedListDao.insertAll(lists[0]);
            return null;
        }
    }

}
