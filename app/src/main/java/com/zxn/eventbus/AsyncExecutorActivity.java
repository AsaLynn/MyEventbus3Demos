package com.zxn.eventbus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.king.base.util.LogUtils;
import com.zxn.eventbus.base.MyBaseActivity;
import com.zxn.eventbus.event.BaseEvent;
import com.zxn.eventbus.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AsyncExecutorActivity extends MyBaseActivity {

    @BindView(R.id.tv_asyncexecutor)
    TextView tvAsyncexecutor;
    @BindView(R.id.btn_async_executor)
    Button btnAsyncExecutor;
    @BindView(R.id.btn_async_executor_exception)
    Button btnAsyncExecutorException;

    @Override
    public void initUI() {
        setContentView(R.layout.activity_async_executor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(MessageEvent event) {
        tvAsyncexecutor.setText(event.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleFailureEvent(ThrowableFailureEvent event) {
        tvAsyncexecutor
                .setText("ThrowableFailureEvent:"
                        .concat(event.getThrowable().getMessage()));
        showToast("recive a ThrowableFailureEvent!");
    }

    private String logMessage(BaseEvent event) {
        String MESSAGE = "MESSAGE-->" + event.message;
        String THREAD_ID = "ThreadId-->" + Thread.currentThread().getId();
        String THREAD_NAME = "ThreadName-->" + Thread.currentThread().getName();
        String str
                = MESSAGE
                .concat("-->")
                .concat(THREAD_NAME)
                .concat("-->")
                .concat(THREAD_ID)
                .concat("-->");
        LogUtils.i(str);
        return str;
    }

    @OnClick({R.id.btn_async_executor, R.id.btn_async_executor_exception})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_async_executor:
                asyncExecutor();
                break;
            case R.id.btn_async_executor_exception:
                asyncExecutorException();
                break;
        }
    }

    private void asyncExecutorException() {
        MyApplication application = (MyApplication) getApplication();
        application.getAsyncExecutor().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                String THREAD_ID = "ThreadId-->" + Thread.currentThread().getId();
                String THREAD_NAME = "ThreadName-->" + Thread.currentThread().getName();
                String str
                        = "this exception event is from async executor!"
                        .concat("-->")
                        .concat(THREAD_NAME)
                        .concat("-->")
                        .concat(THREAD_ID)
                        .concat("-->");
                int x = 100 / 0;
                EventBus
                        .getDefault()
                        .post(new MessageEvent(str));
            }
        });
    }

    private void asyncExecutor() {
        MyApplication application = (MyApplication) getApplication();
        application.getAsyncExecutor().execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {

                String THREAD_ID = "ThreadId-->" + Thread.currentThread().getId();
                String THREAD_NAME = "ThreadName-->" + Thread.currentThread().getName();
                String str
                        = "this event is from async executor!"
                        .concat("-->")
                        .concat(THREAD_NAME)
                        .concat("-->")
                        .concat(THREAD_ID)
                        .concat("-->");
                EventBus
                        .getDefault()
                        .post(new MessageEvent(str));
            }
        });
    }
}
