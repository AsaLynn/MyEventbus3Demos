package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.base.BaseActivity;
import com.king.base.util.LogUtils;
import com.zxn.eventbus.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 在同一个页面中,
 * 订阅消息,
 * 发送消息,
 * 接收消息,
 * 取消订阅
 */
public class EventBusActivity extends BaseActivity {

    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.btn_thread)
    Button btnThread;
    @BindView(R.id.btn_thread2)
    Button btnThread2;
    @BindView(R.id.btn_thread3)
    Button btnThread3;
    @BindView(R.id.btn_main)
    Button btn_main;


    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        //订阅注册
        EventBus
                .getDefault()
                .register(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //订阅注销
        EventBus
                .getDefault()
                .unregister(this);
    }

    //订阅接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        tvShow.setText(event);
    }

    //订阅接收事件对象
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSomethingElse(MessageEvent event) {
        LogUtils.i("handleSomethingElse---->" + event.message);
        tvShow.setText(event.message);
    }

    @OnClick({R.id.btn_thread,
            R.id.btn_thread2,
            R.id.btn_thread3,
            R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_thread:
                thread();
                break;
            case R.id.btn_thread2:
                thread2();
                break;
            case R.id.btn_thread3:
                thread3();
                break;
            case R.id.btn_main:
                threadMain();
                break;
        }
    }

    private void threadMain() {
        EventBus
                .getDefault()
                .post("messag is from mainthread by eventbus... ...");
    }

    private void thread3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送订阅的事件.
                MessageEvent event
                        = new MessageEvent("MessageEvent obj is from thread3 by eventbus ...");
                EventBus
                        .getDefault()
                        .post(event);
            }
        })
                .start();
    }

    private void thread2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送订阅的事件.
                EventBus
                        .getDefault()
                        .post("messag is from thread2 by eventbus... ...");
            }
        })
                .start();
    }

    private void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //发送订阅的事件.
                EventBus
                        .getDefault()
                        .post("messag is from thread by eventbus... ...");
            }
        })
                .start();
    }


}
