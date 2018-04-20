package com.zxn.eventbus;

import android.widget.Button;
import android.widget.EditText;

import com.zxn.eventbus.base.MyBaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusBActivity extends MyBaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.btn_close)
    Button btnClose;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_b);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(this.getClass().getSimpleName());
    }

    @OnClick(R.id.btn_close)
    public void onViewClicked() {
        //发送消息
        EventBus
                .getDefault()
                .post(et.getText().toString().concat("eventbus is coming backing!"));
        finish();
    }
}
