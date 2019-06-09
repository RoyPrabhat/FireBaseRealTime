package com.example.homescreenassignment.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

import com.example.homescreenassignment.main.MainActivity;
import com.example.homescreenassignment.R;
import com.example.homescreenassignment.login.SignUpActivity;
import com.example.homescreenassignment.util.LoginUtil;

public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LoginUtil.isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);

    }
}
