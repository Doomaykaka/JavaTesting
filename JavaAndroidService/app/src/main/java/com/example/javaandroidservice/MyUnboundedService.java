package com.example.javaandroidservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyUnboundedService extends Service {
    public MyUnboundedService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Toast t = new Toast(this);
        t.setText("Hello free");
        t.show();

        stopSelf();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}