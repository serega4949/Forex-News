package com.balinasoft.forexnews.networking;

import com.balinasoft.forexnews.models.RSSfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Serega on 06.08.2016.
 */
public interface API {
    @GET("GetLiveNewsRss")
    Call<RSSfeed> getLiveNews();

    @GET("GetAnalyticsRss")
    Call<RSSfeed> getAnalytics();
}
