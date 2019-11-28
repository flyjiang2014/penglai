package com.penglai.haima.jpush;

import android.content.Context;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import in.srain.cube.util.CLog;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";
    private Context mContext;


    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        CLog.d(TAG, "[onMessage] " + customMessage);
        //  processCustomMessage(context,customMessage);
        this.mContext = context;
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        CLog.d(TAG, "[onNotifyMessageOpened] " + message.notificationExtras);
    }
}
