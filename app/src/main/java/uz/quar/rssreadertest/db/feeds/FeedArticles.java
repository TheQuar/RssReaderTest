package uz.quar.rssreadertest.db.feeds;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"title"}, unique = true)})
public class FeedArticles {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "title")
    String title;
    String feedUrl;
    String link;
    String imageUrl;
    String description;

    @Ignore
    public FeedArticles(int id, String title, String feedUrl, String link, String imageUrl, String description) {
        this.id = id;
        this.title = title;
        this.feedUrl = feedUrl;
        this.link = link;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public FeedArticles(String title, String feedUrl, String link, String imageUrl, String description) {
        this.title = title;
        this.feedUrl = feedUrl;
        this.link = link;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
