package com.detolv.myautocompletetv.sms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by detolv on 8/13/17.
 */

public class HeadlessSmsSendService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
