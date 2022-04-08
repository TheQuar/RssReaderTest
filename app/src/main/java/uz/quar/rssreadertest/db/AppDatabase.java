package uz.quar.rssreadertest.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uz.quar.rssreadertest.db.feeds.FeedArticles;
import uz.quar.rssreadertest.db.feeds.FeedListDao;
import uz.quar.rssreadertest.db.news.NewsArticlesDao;
import uz.quar.rssreadertest.db.news.NewsArticles;
import uz.quar.rssreadertest.utils.Constants;

@Database(entities = {NewsArticles.class, FeedArticles.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;


    public abstract NewsArticlesDao articlesDao();
    public abstract FeedListDao feedListDao();


    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, Constants.FILE_NAME_DB)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }




}

