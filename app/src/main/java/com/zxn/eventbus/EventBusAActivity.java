package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.base.util.LogUtils;
import com.zxn.eventbus.base.MyBaseActivity;
import com.zxn.eventbus.event.StickyBackEvent;

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
    @BindView(R.id.btn_c)
    Button btnC;

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

    @OnClick({R.id.btn, R.id.btn_c})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                startActivity(EventBusBActivity.class);
                break;
            case R.id.btn_c:
                EventBus.getDefault().postSticky("this event is come from A!");
                startActivity(EventBusCActivity.class);
                break;
        }
    }

    @Subscribe()
    public void onStickyBackEvent(StickyBackEvent event) {
        String concat = event.message.concat("-------->onStickyBackEvent");
        LogUtils.i(concat);
        showToast(concat);
        tv.setText(concat);
    }

    @Subscribe()
    public void onStickyBackBooleanEvent(Boolean event) {
        String concat = "onStickyBackBooleanEvent--->boolean result:".concat(String.valueOf(event));
        LogUtils.i(concat);
        showToast(concat);
        tv.setText(concat);
    }
}
