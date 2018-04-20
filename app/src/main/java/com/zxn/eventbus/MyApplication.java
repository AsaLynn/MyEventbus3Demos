package com.zxn.eventbus;

import android.app.Application;

import com.zxn.eventbus.index.MyEventBusIndex;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

public class MyApplication extends Application {

    private AsyncExecutor asyncExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        initEventBus();
    }

    private void initEventBus() {
        //配置没有订阅者的情况下保持静默
        //配置异常处理情况
        //配置调试模式可以排除异常
        //配置全局的单例模式.
        //配置使用EventBusIndex进行优化
        EventBus eventBus = EventBus
                .builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(true)
                .throwSubscriberException(BuildConfig.DEBUG)
                .addIndex(new MyEventBusIndex())
                .installDefaultEventBus();

        asyncExecutor = AsyncExecutor.create();
    }

    public AsyncExecutor getAsyncExecutor() {
        return asyncExecutor;
    }
}
