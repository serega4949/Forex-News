package com.balinasoft.forexnews.networking;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by root on 06.08.16.
 */
public class RestClient {
    private static final String BASE_URL = "https://widgets.fxopen.com:8088/";
    private static RestClient instance = new RestClient();
    private static API api;

    public static API getAPI() {
        return api;
    }

    private RestClient() {

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("widgets.spotfxbroker.com", sslSession);
            }
        };

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(3);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .hostnameVerifier(hostnameVerifier)
                .dispatcher(dispatcher)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .client(client)
                .build();

        api = retrofit.create(API.class);
    }
}
