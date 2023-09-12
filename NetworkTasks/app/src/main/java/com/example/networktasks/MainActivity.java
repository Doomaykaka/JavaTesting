package com.example.networktasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start AsyncTask
        new HTTPAsyncTask().execute();

    }

    class HTTPAsyncTask extends AsyncTask<Void, Void, String> {

        //address
        String addr = "https://google.com";

        //getting response
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                byte[] page = new byte[2048];
                URL data = new URL(addr);
                HttpURLConnection connection = (HttpURLConnection) data.openConnection();
                InputStream is = connection.getInputStream();
                int status = 0;
                while (status != -1) {
                    status = is.read(page);
                    result.append(new String(page, StandardCharsets.UTF_8));
                    for(int i=0; i<page.length;i++)
                        page[i]=0;
                }
                connection.disconnect();
                System.out.println(result);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result.toString();
        }

        //print result
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView field = findViewById(R.id.field);
            field.setText(result.toString());
            field.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}