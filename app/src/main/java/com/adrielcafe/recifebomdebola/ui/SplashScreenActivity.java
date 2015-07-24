package com.adrielcafe.recifebomdebola.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.adrielcafe.recifebomdebola.Db;
import com.adrielcafe.recifebomdebola.R;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Db.loadDb(SplashScreenActivity.this);
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}