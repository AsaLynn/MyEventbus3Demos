package com.zxn.eventbus;

import android.widget.Button;
import android.widget.EditText;

import com.king.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusBActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.btn_close)
    Button btnClose;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_event_bus_b);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

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
