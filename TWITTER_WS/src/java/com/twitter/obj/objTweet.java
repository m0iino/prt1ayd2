/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitter.obj;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Kenia
 */
public class objTweet {

    @XmlElement(name = "token")
    private String token;

    @XmlElement(name = "key")
    private String key;

    @XmlElement(name = "user")
    private String user;

    @XmlElement(name = "tweet")
    private String tweet;

    @XmlElement(name = "date")
    private String date;

    @XmlElement(name = "time")
    private String time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    

}
