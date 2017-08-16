package com.pyapps.practice.Models;

/**
 * Created by prudh on 8/14/2017.
 */

public class Message {
    String content;
    String from;
    String to;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Message(String content, String from, String to) {
        this.content = content;
        this.from = from;
        this.to = to;
    }
}
