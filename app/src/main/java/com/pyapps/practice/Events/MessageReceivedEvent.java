package com.pyapps.practice.Events;

import com.pyapps.practice.Models.Message;

/**
 * Created by prudh on 8/14/2017.
 */

public class MessageReceivedEvent {

    Object sender;
    Message message;

    public Object getSender() {
        return sender;
    }

    public Message getMessage() {
        return message;
    }

    public MessageReceivedEvent(Object sender,Message message) {
        this.sender = sender;
        this.message = message;
    }
}
