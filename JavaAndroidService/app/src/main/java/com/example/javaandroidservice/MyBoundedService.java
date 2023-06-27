package com.example.javaandroidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyBoundedService extends Service {
    private MyBinder myBin = new MyBinder();

    public MyBoundedService() {
    }

    public class MyBinder extends Binder{
        public void printMessage(String text){
            MyBoundedService.this.printMessageFromService(text);
        }
    }

    public void printMessageFromService(String text){
        Toast.makeText(MyBoundedService.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {

        Toast t = new Toast(this);
        t.setText("Hello activity");
        t.show();

        return this.myBin;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}