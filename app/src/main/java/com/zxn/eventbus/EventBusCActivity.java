package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zxn.eventbus.base.MyBaseActivity;
import com.zxn.eventbus.event.StickyBackEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusCActivity extends MyBaseActivity {
    @BindView(R.id.tv_sticky_event)
    TextView tvStickyEvent;
    @BindView(R.id.tv_sticky_back)
    TextView tvStickyBack;
    @BindView(R.id.btn_close)
    Button btnClose;
    @BindView(R.id.btn_close2)
    Button btnClose2;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_eventbus_c);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onStickyTextEvent(String event) {
        tvStickyEvent.setTextColor(red);
        tvStickyEvent.setText(event);
    }

    @BindColor(R.color.colorAccent)
    int red;


    @OnClick({R.id.btn_close, R.id.btn_close2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                EventBus.getDefault().post(new StickyBackEvent("this event is return by C!"));
                finish();
                break;
            case R.id.btn_close2:
                EventBus.getDefault().post(true);
                finish();
                break;
        }
    }
}
