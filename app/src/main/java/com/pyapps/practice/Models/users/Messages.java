package com.pyapps.practice.Models.users;

import java.util.Map;

public class Messages{
	private Map<String,Boolean> received;
	private Map<String,Boolean> sent;

    public Map<String, Boolean> getReceived() {
        return received;
    }

    public void setReceived(Map<String, Boolean> received) {
        this.received = received;
    }

    public Map<String, Boolean> getSent() {
        return sent;
    }

    public void setSent(Map<String, Boolean> sent) {
        this.sent = sent;
    }

    public Messages() {
    }
}
