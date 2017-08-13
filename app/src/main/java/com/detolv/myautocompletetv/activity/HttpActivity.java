package com.detolv.myautocompletetv.activity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.detolv.myautocompletetv.IInsertSmsListener;
import com.detolv.myautocompletetv.NanoHttpServer;
import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpActivity extends Activity implements IInsertSmsListener {
    private static final String ADDRESS = "address";
    private static final String BODY = "body";
    private static final String SMS_URI = "content://sms";
    private NanoHttpServer nanoHttpServer;
    private Context context;
    @BindView(R.id.tips_tv)  TextView mTipsTextView;
    @BindView(R.id.change_btn)
    Button mChangeBtn;

    public static void navigateTo(Activity activity) {
        Intent intent = new Intent(activity, HttpActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);
        changeSmsApp();
        context = this;
        nanoHttpServer = new NanoHttpServer();
        nanoHttpServer.setiInsertSmsListener(this);
        mTipsTextView.setText("请在远程浏览器中输入:\n\n"+getLocalIpStr(this)+":"+NanoHttpServer.DEFAULT_SERVER_PORT);
        try {
            nanoHttpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
            mTipsTextView.setText(e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        nanoHttpServer.stop();
        super.onDestroy();
    }

    private void sendSMS(String phone, String content) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(ADDRESS, phone);
        values.put(BODY, content);
        Uri uri = cr.insert(Uri.parse(SMS_URI), values);
        final String subString = uri.toString().substring(SMS_URI.length() + 1, uri.toString().length());
        if (!subString.equals("0")){
            ToastUtil.showToastOnUIThread(this, "短信发送成功  smsCode: " + subString);
        }else {
            ToastUtil.showToastOnUIThread(this, "短信发送失败");
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void changeSmsApp(){
        final String myPackageName = getPackageName();
        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
            mChangeBtn.setVisibility(View.VISIBLE);
            mChangeBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
                    startActivity(intent);
                }
            });
        } else {
            mChangeBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSendSms(String phone, String content) {
        sendSMS(phone, content);
    }
    private String getLocalIpStr(Context context) {
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return intToIpAddr(wifiInfo.getIpAddress());
    }
    private static String intToIpAddr(int ip) {
        return (ip & 0xff) + "." + ((ip>>8)&0xff) + "." + ((ip>>16)&0xff) + "." + ((ip>>24)&0xff);
    }
}
