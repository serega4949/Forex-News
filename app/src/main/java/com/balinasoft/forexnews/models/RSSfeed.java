package com.balinasoft.forexnews.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by root on 06.08.16.
 */

@Root(name = "rss", strict = false)
public class RSSfeed {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

