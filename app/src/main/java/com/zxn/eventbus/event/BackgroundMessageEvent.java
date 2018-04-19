package com.zxn.eventbus.event;

public class BackgroundMessageEvent extends BaseEvent {
    public BackgroundMessageEvent(String message) {
        super(message);
    }
}
