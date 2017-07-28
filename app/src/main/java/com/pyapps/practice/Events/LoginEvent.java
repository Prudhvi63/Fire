package com.pyapps.practice.Events;

/**
 * Created by prudh on 7/27/2017.
 */

public class LoginEvent extends BaseEvent{
    String uid;

    public LoginEvent(Object sender,String uid) {
        this.uid = uid;
        this.sender = sender;
    }

    public String getUid() {
        return uid;
    }
}
