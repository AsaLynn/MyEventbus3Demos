package com.zxn.eventbus;

import android.widget.Button;
import android.widget.TextView;

import com.king.base.util.LogUtils;
import com.zxn.eventbus.base.MyBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusAActivity extends MyBaseActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_a);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(this.getClass().getSimpleName());
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
    //threadMode = ThreadMode.MAIN
    @Subscribe()
    public void onEventBusMessage(String msg) {
        String concat = msg.concat("-------->");
        LogUtils.i(concat);
        showToast(concat);
        tv.setText(concat);
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(EventBusBActivity.class);
    }
}
