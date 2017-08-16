package com.pyapps.practice.Events;

import com.pyapps.practice.Models.Message;
import com.pyapps.practice.Models.users.Messages;

/**
 * Created by prudh on 8/14/2017.
 */

public class MessageReceivedEvent {

    Message message;

    public Message getMessage() {
        return message;
    }
}
