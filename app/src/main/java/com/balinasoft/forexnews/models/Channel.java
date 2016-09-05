package com.balinasoft.forexnews.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by root on 06.08.16.
 */
public class Channel {
    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @ElementList(entry = "item", inline = true)
    private ArrayList<News> news;

    public ArrayList<News> getNews() {
        return news;
    }

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }
}
