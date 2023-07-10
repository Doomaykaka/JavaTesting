package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitleFragment youFragment = new TitleFragment();
        WebFragment webFragment = new WebFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()          // получаем экземпляр FragmentTransaction
                .add(R.id.container, youFragment)
                .add(R.id.container2, webFragment)
                .addToBackStack("myStack")
                .commit();            // вызываем commit для совершения действий FragmentTransaction
    }
}