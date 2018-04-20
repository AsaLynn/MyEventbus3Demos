package com.zxn.eventbus;

import android.widget.Button;
import android.widget.TextView;

import com.king.base.util.LogUtils;
import com.zxn.eventbus.base.MyBaseActivity;
import com.zxn.eventbus.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrioritiesActivity extends MyBaseActivity {


    @BindView(R.id.tv_priorities0)
    TextView tvPriorities0;
    @BindView(R.id.tv_priorities1)
    TextView tvPriorities1;
    @BindView(R.id.tv_priorities2)
    TextView tvPriorities2;
    @BindView(R.id.btn_priorities)
    Button btnPriorities;
    @BindView(R.id.tv_priorities10)
    TextView tvPriorities10;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_priorities);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_priorities)
    public void onViewClicked() {
        Runnable runnable = new Runnable() {
            public void run() {
                EventBus
                        .getDefault()
                        .post(new MessageEvent("hello priorities message is coming!"));
            }
        };
        new Thread(runnable).start();
    }

    @Subscribe
    public void onEvent0(final MessageEvent event) {
        final String text = "onEvent0".concat(logEvent(event));
        LogUtils.i(text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPriorities0.setText(text);
            }
        });
    }

    @Subscribe(priority = 1)
    public void onEvent1(MessageEvent event) {
        final String text = "onEvent1".concat(logEvent(event));
        LogUtils.i(text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPriorities1.setText(text);
            }
        });
    }

    @Subscribe(priority = 2)
    public void onEvent2(MessageEvent event) {
        final String text = "onEvent2".concat(logEvent(event));
        LogUtils.i(text);
        //中断后续的事件传递
        EventBus.getDefault().cancelEventDelivery(event) ;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPriorities2.setText(text);
            }
        });
    }

    @Subscribe(priority = 10)
    public void onEvent10(MessageEvent event) {
        final String text = "onEvent10".concat(logEvent(event));
        LogUtils.i(text);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPriorities10.setText(text);
            }
        });
    }

    private String logEvent(MessageEvent event) {
        String tag = "-->";
        String s = tag.concat(event.message)
                .concat(tag)
                .concat("currentTime:")
                .concat(String.valueOf(System.currentTimeMillis()));
        return s;
    }
}
