package com.example.vitalyyurenya.appetizer.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AppetizerAccountService extends Service {
    AppetizerAuthenticator authenticator;

    @Override
    public void onCreate() {
        authenticator = new AppetizerAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
