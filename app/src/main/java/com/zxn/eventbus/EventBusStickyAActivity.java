package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxn.eventbus.base.MyBaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusStickyAActivity extends MyBaseActivity {


    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.btn_sticky_send)
    Button btnStickySend;
    @BindView(R.id.btn_sticky_c)
    Button btnStickyC;
    @BindView(R.id.tv_c)
    TextView tvC;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_sticky);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_sticky_send, R.id.btn_sticky_c})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sticky_send:
                //在注册之前发送消息
                String str = tvA.getText().toString().trim();
                EventBus
                        .getDefault()
                        .postSticky(str);
                startActivity(EventBusStickyBActivity.class);
                break;
            case R.id.btn_sticky_c:
                //在注册之前发送消息
                String str2 = tvC.getText().toString().trim();
                EventBus
                        .getDefault()
                        .postSticky(str2);
                startActivity(EventBusStickyCActivity.class);
                break;
        }
    }


}
