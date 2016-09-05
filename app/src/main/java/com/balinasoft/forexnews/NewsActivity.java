package com.balinasoft.forexnews;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.balinasoft.forexnews.adapters.NewsPagerAdapter;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] tabTitles = {getString(R.string.text_live_news), getString(R.string.text_analytics)};
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), tabTitles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.typeNewsPager);
        viewPager.setAdapter(newsPagerAdapter);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
