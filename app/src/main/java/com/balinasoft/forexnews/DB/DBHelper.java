package com.balinasoft.forexnews.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.balinasoft.forexnews.models.AnalyticsNews;
import com.balinasoft.forexnews.models.LiveNews;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by root on 06.08.16.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "forex news21";
    private static final int DATABASE_VERSION = 1;
    private Dao<LiveNews, String> daoLiveNews;
    private Dao<AnalyticsNews, String> daoAnalyticsNews;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LiveNews.class);
            TableUtils.createTable(connectionSource, AnalyticsNews.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, LiveNews.class, false);
            TableUtils.dropTable(connectionSource, AnalyticsNews.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<LiveNews, String> getDaoLiveNews() throws SQLException {
        if (daoLiveNews == null) {
            daoLiveNews = getDao(LiveNews.class);
        }
        return daoLiveNews;
    }

    public Dao<AnalyticsNews, String> getDaoAnalyticsNews() throws SQLException {
        if (daoAnalyticsNews == null) {
            daoAnalyticsNews = getDao(AnalyticsNews.class);
        }
        return daoAnalyticsNews;
    }

    @Override
    public void close() {
        super.close();
        daoLiveNews = null;
        daoAnalyticsNews = null;
    }
}
