package com.balinasoft.forexnews.models;

import com.j256.ormlite.field.DatabaseField;

import org.simpleframework.xml.Element;

/**
 * Created by Serega on 06.08.2016.
 */

@Element(name = "item")
public class News {
    public static final String FIELD_PUB_DATE = "pubDate";

    @DatabaseField
    @Element(name = "title", required = false)
    private String title;

    @DatabaseField
    @Element(name = "description", required = false)
    private String description;

    @DatabaseField(columnName = FIELD_PUB_DATE, id = true)
    @Element(name = "pubDate", required = false)
    private String pubDate;

    public News() {
    }

    public News(String title, String description, String pubDate) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (description != null ? !description.equals(news.description) : news.description != null)
            return false;
        return pubDate != null ? pubDate.equals(news.pubDate) : news.pubDate == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
        return result;
    }
}
