package com.zxn.eventbus;

import android.widget.Button;
import android.widget.TextView;

import com.king.base.util.LogUtils;
import com.zxn.eventbus.base.MyBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusStickyBActivity extends MyBaseActivity {

    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.btn_close_page)
    Button btnClosePage;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_sticky_b);
        ButterKnife.bind(this);
        EventBus
                .getDefault()
                .register(this);
    }

    @OnClick(R.id.btn_close_page)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault()
                .unregister(this);
    }

    //接收上个页面传递过来的事件
    //注册之后,接收消息
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onStickyMessage(String event) {
        tvB.setText(event);
        LogUtils.i(event.concat("----->onStickyMessage!"));
    }
}
