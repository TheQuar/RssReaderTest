package uz.quar.rssreadertest.db.news;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsArticlesDao {
    @Query("SELECT * FROM NewsArticles")
    LiveData<List<NewsArticles>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsArticles> newsArticles);

    @Query("DELETE FROM NewsArticles WHERE sourceUrl LIKE '%' || :sourceUrl || '%'")
    void deleteAllBySourceUrl(String sourceUrl);
}
