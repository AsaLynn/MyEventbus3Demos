package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.base.BaseActivity;
import com.king.base.util.LogUtils;
import com.zxn.eventbus.event.AsyncMessageEvent;
import com.zxn.eventbus.event.BackgroundMessageEvent;
import com.zxn.eventbus.event.BaseEvent;
import com.zxn.eventbus.event.MainMessageEvent;
import com.zxn.eventbus.event.MessageEvent;
import com.zxn.eventbus.event.OrderedMessageEvent;
import com.zxn.eventbus.event.PostingMessageEvent;
import com.zxn.eventbus.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusThreadModeActivity extends BaseActivity {


    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.btn_postting)
    Button btnPostting;
    @BindView(R.id.btn_main)
    Button btnMain;
    @BindView(R.id.btn_ordered)
    Button btnOrdered;
    @BindView(R.id.btn_background)
    Button btnBackground;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_thread_mode);
        ButterKnife.bind(this);
        EventBus
                .getDefault()
                .register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus
                .getDefault()
                .unregister(this);
    }

    //发送消息在什么线程,接收消息就在什么线程
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventThreadModePosting(MessageEvent event) {
        final String str = logMessage(event);
        if (UIUtils.isMainThread()) {
            tvMode.setText(str);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMode.setText(str);
                }
            });
        }
    }

    //无论发送消息在什么线程,接收消息都在主线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadModeMain(MainMessageEvent event) {
        final String str = logMessage(event);
        if (UIUtils.isMainThread()) {
            tvMode.setText(str);
        }
    }

    //无论发送消息在什么线程,接收消息都在新开辟的子线程中执行
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventThreadModeASYNC(AsyncMessageEvent event) {
        final String str = logMessage(event);
        if (UIUtils.isMainThread()) {
            tvMode.setText(str);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMode.setText(str);
                }
            });
        }
    }

    //如果发送消息在主线程,接收消息都在新开辟的子线程中执行,
    //如果发送消息在子线程,接收消息和发送消息在相同的线程.
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventThreadModeBackground(BackgroundMessageEvent event) {
        final String str = logMessage(event);
        if (UIUtils.isMainThread()) {
            tvMode.setText(str);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMode.setText(str);
                }
            });
        }
    }

    //先发消息先处理,处理在主线程执行.
    //如果发送消息在不同的异步线程,那么先加入队列先处理.
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onEventThreadModeMAIN_ORDERED(OrderedMessageEvent event) {
        final String str = logMessage(event);
        if (UIUtils.isMainThread()) {
            tvMode.setText(str);
        }
    }

    private String logMessage(BaseEvent event) {
        String MESSAGE = "MESSAGE-->" + event.message;
        String THREAD_ID = "ThreadId-->" + Thread.currentThread().getId();
        String THREAD_NAME = "ThreadName-->" + Thread.currentThread().getName();
        String str
                = MESSAGE
                .concat("-->")
                .concat(THREAD_NAME)
                .concat("-->")
                .concat(THREAD_ID)
                .concat("-->");
        LogUtils.i(str);
        return str;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick({R.id.btn_postting,
            R.id.btn_postting_thread,
            R.id.btn_main,
            R.id.btn_async_main,
            R.id.btn_async_thread,
            R.id.btn_background_thread,
            R.id.btn_ordered,
            R.id.btn_ordered_more_thread,
            R.id.btn_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_postting:
                //ThreadMode: POSTING-->主线程中发送
                String postting_main_msg = "MessageEvent is from ThreadMode POSTING:on mainThread";
                post(new PostingMessageEvent(postting_main_msg));
                break;
            case R.id.btn_postting_thread:
                //ThreadMode: POSTING-->子线程中发送
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String postting_thread_msg = "MessageEvent is from ThreadMode POSTING:on asynchronous";
                        post(new PostingMessageEvent(postting_thread_msg));
                    }
                }).start();
                break;
            case R.id.btn_main:
                //ThreadMode: MAIN-->子线程中发送
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String postting_thread_msg = "MessageEvent is from ThreadMode MAIN:on asynchronous";
                        post(new MainMessageEvent(postting_thread_msg));
                    }
                }).start();
                break;
            case R.id.btn_async_main:
                //ThreadMode: Async-->主线程中发送
                String async_main_msg = "MessageEvent is from ThreadMode Async:on MAIN";
                post(new AsyncMessageEvent(async_main_msg));
                break;
            case R.id.btn_async_thread:
                //ThreadMode: Async-->子线程中发送
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String postting_thread_msg = "MessageEvent is from ThreadMode Async:on asynchronous";
                        post(new AsyncMessageEvent(postting_thread_msg));
                    }
                }).start();
                break;
            case R.id.btn_background:
                //ThreadMode.BACKGROUND-->主线程中发送
                String background_main_msg = "MessageEvent is from ThreadMode background:on MAIN";
                post(new BackgroundMessageEvent(background_main_msg));
                break;
            case R.id.btn_background_thread:
                //ThreadMode.BACKGROUND-->子线程中发送
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String btn_background_thread_msg = "MessageEvent is from ThreadMode background:on asynchronous";
                        post(new BackgroundMessageEvent(btn_background_thread_msg));
                    }
                }).start();
                break;
            case R.id.btn_ordered:
                //ThreadMode: MAIN_ORDERED-->单线程中发送
                String btn_background_thread_msg = "MessageEvent is from ThreadMode MAIN_ORDERED:on MAIN";
                post(new OrderedMessageEvent(btn_background_thread_msg));

                String btn_background_thread_msg1 = "MessageEvent1 is from ThreadMode MAIN_ORDERED:on MAIN";
                post(new OrderedMessageEvent(btn_background_thread_msg1));

                String btn_background_thread_msg2 = "MessageEvent2 is from ThreadMode MAIN_ORDERED:on MAIN";
                post(new OrderedMessageEvent(btn_background_thread_msg2));
                break;
            case R.id.btn_ordered_more_thread:
                //ThreadMode: MAIN_ORDERED-->多线程中发送
                for (int i = 0; i < 6; i++) {
                    final String msg = "MessageEvent is from ThreadMode MAIN_ORDERED:on more thread-->" + i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            post(new OrderedMessageEvent(msg));
                        }
                    }).start();
                }
                break;
        }
    }

    //发送消息.
    private void post(BaseEvent event) {
        EventBus
                .getDefault()
                .post(event);
    }

}
