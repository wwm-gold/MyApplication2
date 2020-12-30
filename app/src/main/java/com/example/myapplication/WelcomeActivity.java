package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    static Thread thread;
    private static int SPLASH_DISPLAY_LENGHT= 1000;    //延迟1秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);	//第二个参数即为执行完跳转后的Activity
                startActivity(intent);
                WelcomeActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}