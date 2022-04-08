package uz.quar.rssreadertest.viewmodel;

import android.app.Application;

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
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.db.news.NewsListRepo;
import uz.quar.rssreadertest.utils.MyDialog;

public class NewsListViewModel extends AndroidViewModel {
    private NewsListRepo newsListRepo;
    public LiveData<List<NewsArticles>> getAllNews;

    public NewsListViewModel(@NonNull Application application) {
        super(application);
        newsListRepo = new NewsListRepo(application);
        getAllNews = newsListRepo.getAllNews();
    }

    public LiveData<List<NewsArticles>> getAllNews() {
        return getAllNews;
    }

    public void insert(List<NewsArticles> feedArticles) {
        newsListRepo.insert(feedArticles);
    }

    public void deleteAll(String url) {
        newsListRepo.deleteAll(url);
    }

    public void refresh() {
        for (FeedArticles feedArticles : newsListRepo.feedListDao.getAllFeedsNow()) {
            fetchFeed(feedArticles.getFeedUrl());
        }

    }

    private void fetchFeed(String url) {
        Parser parser = new Parser.Builder()
//                .charset(Charset.forName("ISO-8859-7"))
                // .cacheExpirationMillis() and .context() not called because on Java side, caching is NOT supported
                .build();

        parser.onFinish(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Channel channel) {
                writeDb(url, channel);
            }

            @Override
            public void onError(Exception e) {
                MyDialog.show(getApplication(), "Error", e.getMessage());
            }
        });
        parser.execute(url);
    }

    private void writeDb(String url, Channel channel) {
        List<NewsArticles> articles = new ArrayList<>();
        for (Article article : channel.getArticles()) {
            articles.add(new NewsArticles(
                    article.getGuid(), article.getTitle(), article.getAuthor(),
                    article.getLink(), article.getPubDate(), article.getDescription(),
                    article.getContent(), article.getImage(), convert(article.getCategories()),
                    url));
        }
        insert(articles);
    }

    private String convert(List<String> list) {
        StringBuilder categories = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                categories.append(list.get(i));
            } else {
                categories.append(list.get(i)).append(", ");
            }
        }
        return categories.toString();
    }
}
