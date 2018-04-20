package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;

import com.king.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick({R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                startActivity(EventBusActivity.class);
                break;
            case R.id.btn1:
                startActivity(EventBusAActivity.class);
                break;
            case R.id.btn2:
                startActivity(EventBusThreadModeActivity.class);
                break;
            case R.id.btn3:
                startActivity(EventBusStickyAActivity.class);
                break;
            case R.id.btn4:
                startActivity(PrioritiesActivity.class);
                break;
        }
    }
}
