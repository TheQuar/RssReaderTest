package uz.quar.rssreadertest.db.news;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"title"}, unique = true)})
public class NewsArticles implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String guid;
    @ColumnInfo(name = "title")
    String title;
    String author;
    String link;
    String pubDate;
    String description;
    String content;
    String image;
    String sourceName;
    String sourceUrl;

    @Ignore
    public NewsArticles(int id, String guid, String title, String author, String link, String pubDate, String description, String content, String image, String sourceName, String sourceUrl) {
        this.id = id;
        this.guid = guid;
        this.title = title;
        this.author = author;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.content = content;
        this.image = image;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
    }

    public NewsArticles(String guid, String title, String author, String link, String pubDate, String description, String content, String image, String sourceName, String sourceUrl) {
        this.guid = guid;
        this.title = title;
        this.author = author;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
        this.content = content;
        this.image = image;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
