package com.zxn.eventbus.event;

public class OrderedMessageEvent extends BaseEvent {
    public OrderedMessageEvent(String message) {
        super(message);
    }
}
