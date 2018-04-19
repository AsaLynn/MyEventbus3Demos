package com.zxn.eventbus.event;

public class AsyncMessageEvent extends BaseEvent {
    public AsyncMessageEvent(String message) {
        super(message);
    }
}
