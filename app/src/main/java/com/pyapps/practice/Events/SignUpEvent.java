package com.pyapps.practice.Events;

/**
 * Created by prudh on 7/27/2017.
 */

public class SignUpEvent extends BaseEvent {
    String uid;

    public SignUpEvent(Object sender,String uid) {
        this.uid = uid;
        this.sender = sender;
    }

    public String getUid() {
        return uid;
    }
}
