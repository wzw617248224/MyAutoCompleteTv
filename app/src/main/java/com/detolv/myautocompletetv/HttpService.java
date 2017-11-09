package com.detolv.myautocompletetv;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.detolv.myautocompletetv.utils.ToastUtil;

import java.io.IOException;

public class HttpService extends Service implements IInsertSmsListener {
    private static Activity mActivity;
    private boolean isWorking = false;
    private NanoHttpServer nanoHttpServer;
    private static final String ADDRESS = "address";
    private static final String BODY = "body";
    private static final String SMS_URI = "content://sms";

    public static void startHttpService(Activity activity, Intent srcIntent){
        Intent intent = new Intent(activity, HttpService.class);
        intent.putExtras(srcIntent);
        activity.startService(intent);
        mActivity = activity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isWorking) {

        }
        else {
            isWorking = true;
            nanoHttpServer = new NanoHttpServer();
            nanoHttpServer.setiInsertSmsListener(this);
            try {
                nanoHttpServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSendSms(String phone, String content) {
        sendSMS(phone, content);
    }

    private void sendSMS(String phone, String content) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(ADDRESS, phone);
        values.put(BODY, content);
        Uri uri = cr.insert(Uri.parse(SMS_URI), values);
        final String subString = uri.toString().substring(SMS_URI.length() + 1, uri.toString().length());
        if (!subString.equals("0")){
            ToastUtil.showToastOnUIThread(mActivity, "短信发送成功  smsCode: " + subString);
        }else {
            ToastUtil.showToastOnUIThread(mActivity, "短信发送失败");
        }
    }

    @Override
    public void onDestroy() {
        nanoHttpServer.stop();
        super.onDestroy();
    }
}
