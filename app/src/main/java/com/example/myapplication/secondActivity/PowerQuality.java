package com.example.myapplication.secondActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.MyApplication;
import com.example.myapplication.util.MsgSocket;

public class PowerQuality extends BaseActivity {

    int[] _03=new int[]{3,39,40,41,42,43,44,45,46,47,48,49,50,51};
    int[] _04=new int[]{4,190};
    int[][] adrr={_03,_04};
    MyApplication mApp= MyApplication.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_quality);

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("电能质量");
        ImageView viewById = (ImageView)findViewById(R.id.iv_back);
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(this);

    }
    public Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {

        }
    };
    protected void onResume(){
        super.onResume();
        MsgSocket instance = MsgSocket.getInstance();
        instance.setParameters(adrr,6,mHandler);

    }

    protected void onPause(){
        super.onPause();
    }
}