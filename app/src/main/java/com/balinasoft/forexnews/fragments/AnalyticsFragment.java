package com.balinasoft.forexnews.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.forexnews.DB.DBHelper;
import com.balinasoft.forexnews.R;
import com.balinasoft.forexnews.adapters.NewsAdapter;
import com.balinasoft.forexnews.models.AnalyticsNews;
import com.balinasoft.forexnews.models.News;
import com.balinasoft.forexnews.models.RSSfeed;
import com.balinasoft.forexnews.networking.RestClient;
import com.balinasoft.forexnews.utils.Checkers;
import com.balinasoft.forexnews.utils.Constants;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnalyticsFragment extends Fragment {

    private List<News> news;
    private NewsAdapter newsAdapter;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), news);

        RecyclerView rvLiveNews = (RecyclerView) view.findViewById(R.id.rvAnalitycs);
        rvLiveNews.setAdapter(newsAdapter);
        rvLiveNews.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Checkers.isOnline(getActivity())) {
            Call<RSSfeed> callNews = RestClient.getAPI().getAnalytics();
            callNews.enqueue(callbackAnalytics);
        } else {
            getDataFromDB();
        }
    }

    private void getDataFromDB() {
        DBHelper dbHelper = new DBHelper(getActivity());
        try {
            List<AnalyticsNews> dbNews = dbHelper.getDaoAnalyticsNews()
                    .queryBuilder()
                    .limit(30l)
                    .orderBy(News.FIELD_PUB_DATE, false)
                    .query();

            if (dbNews.size() != 0) {
                news.addAll(dbNews);
                newsAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    private void setDataToDB(List<News> newItems) {
        DBHelper dbHelper = new DBHelper(getActivity());
        Dao<AnalyticsNews, String> dao = null;
        try {
            dao = dbHelper.getDaoAnalyticsNews();
            List<AnalyticsNews> dbNews = dao.queryForAll();
            long sumItems = dbNews.size() + newItems.size();

            if (sumItems > 30) {
                QueryBuilder<AnalyticsNews, String> builder = dao.queryBuilder();
                builder.limit(sumItems - 30);
                builder.orderBy(News.FIELD_PUB_DATE, true);  // true for ascending, false for descending
                List<AnalyticsNews> oldItems = dao.query(builder.prepare());
                dao.delete(oldItems);
            }

            for (News item : newItems) {
                dao.createIfNotExists(new AnalyticsNews(item));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    private Callback<RSSfeed> callbackAnalytics = new Callback<RSSfeed>() {
        @Override
        public void onResponse(Call<RSSfeed> call, Response<RSSfeed> response) {
            if (response.isSuccessful()) {
                List<News> newItems = response.body().getChannel().getNews();
                if (newItems.size() != 0) {
                    news.addAll(newItems);
                    newsAdapter.notifyDataSetChanged();
                    setDataToDB(newItems);
                    SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SP_FOREX_NEWS, Context.MODE_PRIVATE);
                    preferences.edit().putString(Constants.LAST_UPDATE_DATE, Checkers.getCurrentDate()).apply();
                    getActivity().sendBroadcast(new Intent(Constants.ACTION_CHANGE_DATE));
                }
            } else {
                getDataFromDB();
            }
        }

        @Override
        public void onFailure(Call<RSSfeed> call, Throwable t) {
            t.printStackTrace();
            getDataFromDB();
        }
    };
}
