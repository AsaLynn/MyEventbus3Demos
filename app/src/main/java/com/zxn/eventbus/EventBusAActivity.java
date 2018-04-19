package com.zxn.eventbus;

import android.widget.Button;
import android.widget.TextView;

import com.king.base.BaseActivity;
import com.king.base.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusAActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_a);
        ButterKnife.bind(this);
        //EventBus使用注册
        EventBus
                .getDefault()
                .register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus注销
        EventBus
                .getDefault()
                .unregister(this);
    }

    //定义方法接收消息.
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusMessage(String msg) {
        LogUtils.i(msg.concat("-------->"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(EventBusBActivity.class);
    }
}
