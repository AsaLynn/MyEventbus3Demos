package com.zxn.eventbus;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.zxn.eventbus.base.MyBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusStickyCActivity extends MyBaseActivity {

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

        //手动管理stickyevent,但是必须定义接收事件的方法.
        String stickyEvent = EventBus
                .getDefault()
                .getStickyEvent(String.class);
        if (TextUtils.isEmpty(stickyEvent)) {
            tvB.setText("sorry,have not reciver the sticky event!");
        } else {
            EventBus
                    .getDefault()
                    .removeAllStickyEvents();
            tvB.setText(stickyEvent);
        }

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
    //注册之后,接收消息,采用手接受事件,此方法不做任何逻辑处理,但是必须定义否则报错.
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onStickyMessage(String event) {
    }
}
