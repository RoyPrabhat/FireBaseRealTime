package com.example.homescreenassignment.datamodel;

import java.io.Serializable;

public class Feed implements Serializable {

    public String feedUserId;
    public String feed;
    public Long time;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Feed() {
    }

    public Feed(String feedUserId, String feed, Long time) {
        this.feedUserId = feedUserId;
        this.feed = feed;
        this.time = time;
    }

    public String getFeedUserId() {
        return feedUserId;
    }

    public void setFeedUserId(String feedUserId) {
        this.feedUserId = feedUserId;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
