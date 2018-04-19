package com.zxn.eventbus.utils;

import android.os.Looper;

public class UIUtils {
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }
}
