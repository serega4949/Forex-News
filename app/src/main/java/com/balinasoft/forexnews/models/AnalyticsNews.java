package com.balinasoft.forexnews.models;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Serega on 07.08.2016.
 */
@DatabaseTable(tableName = "AnalyticsNews")
public class AnalyticsNews extends News {

    public AnalyticsNews() {
    }

    public AnalyticsNews(News item) {
        super(item.getTitle(), item.getDescription(), item.getPubDate());
    }
}
