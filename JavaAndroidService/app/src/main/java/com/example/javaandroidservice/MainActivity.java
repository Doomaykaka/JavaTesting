package com.example.javaandroidservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyBoundedService.MyBinder serviceBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            serviceBinder = (MyBoundedService.MyBinder) service;

            serviceBinder.printMessage("Sayed");

            serviceBinder.printMessage("Bye");

            unbindService(mConnection);
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected&mdash;that is, its process crashed.
            serviceBinder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unbounded service example
        //Intent unbIntent = new Intent(this, MyUnboundedService.class);
        //startService(unbIntent);

        //Bounded service example
        Intent bIntent = new Intent(this, MyBoundedService.class);
        bindService(bIntent,mConnection, Context.BIND_AUTO_CREATE);
    }
}