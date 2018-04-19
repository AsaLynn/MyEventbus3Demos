package com.zxn.eventbus.event;

public class PostingMessageEvent extends BaseEvent {
    public PostingMessageEvent(String message) {
        super(message);
    }
}
