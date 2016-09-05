package com.balinasoft.forexnews.models;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Serega on 07.08.2016.
 */
@DatabaseTable(tableName = "LiveNews")
public class LiveNews extends News {
    public LiveNews() {
    }

    public LiveNews(News item) {
        super(item.getTitle(), item.getDescription(), item.getPubDate());
    }
}
