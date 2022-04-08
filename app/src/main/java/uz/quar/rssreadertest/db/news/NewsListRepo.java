package uz.quar.rssreadertest.db.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uz.quar.rssreadertest.db.AppDatabase;
import uz.quar.rssreadertest.db.feeds.FeedListDao;

public class NewsListRepo {
    public NewsArticlesDao articlesDao;
    public FeedListDao feedListDao;
    private AppDatabase appDatabase;

    public LiveData<List<NewsArticles>> getAllNews;

    public NewsListRepo(Application application) {
        appDatabase = AppDatabase.getInstance(application);
        articlesDao = appDatabase.articlesDao();
        feedListDao = appDatabase.feedListDao();
        getAllNews = articlesDao.getAllNews();
    }

    public LiveData<List<NewsArticles>> getAllNews() {
        return getAllNews;
    }

    public void insert(List<NewsArticles> feedArticles) {
        new InsertAsyncTask(articlesDao).execute(feedArticles);
    }

    public void deleteAll(String url) {
        new DeleteAsyncTask(articlesDao).execute(url);
    }

    private class InsertAsyncTask extends AsyncTask<List<NewsArticles>, Void, Void> {
        NewsArticlesDao articlesDao;

        public InsertAsyncTask(NewsArticlesDao articlesDao) {
            this.articlesDao = articlesDao;
        }

        @Override
        protected Void doInBackground(List<NewsArticles>... lists) {
            articlesDao.insertAll(lists[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<String, Void, Void> {
        NewsArticlesDao articlesDao;

        public DeleteAsyncTask(NewsArticlesDao articlesDao) {
            this.articlesDao = articlesDao;
        }

        @Override
        protected Void doInBackground(String... lists) {
            articlesDao.deleteAllBySourceUrl(lists[0]);
            return null;
        }
    }

}
