package uz.quar.rssreadertest.db.feeds;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedListDao {
    @Query("SELECT * FROM FeedArticles")
    LiveData<List<FeedArticles>> getAllFeeds();

    @Query("SELECT * FROM FeedArticles")
    List<FeedArticles> getAllFeedsNow();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FeedArticles> feedArticles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FeedArticles feedArticles);

    @Delete
    void delete(FeedArticles feedArticles);
}
