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

import com.detolv.myautocompletetv.HttpService;
import com.detolv.myautocompletetv.IInsertSmsListener;
import com.detolv.myautocompletetv.NanoHttpServer;
import com.detolv.myautocompletetv.R;
import com.detolv.myautocompletetv.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpActivity extends Activity {
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
        HttpService.startHttpService(this, getIntent());
        mTipsTextView.setText("请在远程浏览器中输入:\n\n"+getLocalIpStr(this)+":"+NanoHttpServer.DEFAULT_SERVER_PORT);
    }


    private String getLocalIpStr(Context context) {
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return intToIpAddr(wifiInfo.getIpAddress());
    }

    private static String intToIpAddr(int ipAddress) {
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void changeSmsApp(){
        final String myPackageName = getPackageName();
        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
            mChangeBtn.setVisibility(View.VISIBLE);
            mChangeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
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
}
