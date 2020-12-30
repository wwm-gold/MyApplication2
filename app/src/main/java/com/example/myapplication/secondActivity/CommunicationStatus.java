package com.example.myapplication.secondActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.MyApplication;
import com.example.myapplication.util.MsgSocket;

public class CommunicationStatus extends BaseActivity {
    TextView slave,master,slaveNO,slaveADDR,slave01,slave02,slave03,slave04,slave05,slave06,slave07,slave08,slave09,slave10,slave11,slave12;
    Drawable gray;
    Drawable green;
    int[] _03=new int[]{3,39,40,41,42,43,44,45,46,47,48,49,50,51};
    int[] _04=new int[]{4,190};
    int[][] adrr={_03,_04};
    MyApplication mApp= MyApplication.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("通讯状态");
        Resources resources = this.getResources();
        gray = resources.getDrawable(R.drawable.gray);
        green=resources.getDrawable(R.drawable.green);

        slave = findViewById(R.id.slave);
        master = findViewById(R.id.master);
        slaveNO = findViewById(R.id.slaveNO);
        slaveADDR = findViewById(R.id.slaveADDR);
        slave01 = findViewById(R.id.slave1);
        slave02 = findViewById(R.id.slave2);
        slave03 = findViewById(R.id.slave3);
        slave04 = findViewById(R.id.slave4);
        slave05 = findViewById(R.id.slave5);
        slave06 = findViewById(R.id.slave6);
        slave07 = findViewById(R.id.slave7);
        slave08 = findViewById(R.id.slave8);
        slave09 = findViewById(R.id.slave9);
        slave10 = findViewById(R.id.slave10);
        slave11 = findViewById(R.id.slave11);
        slave12 = findViewById(R.id.slave12);
    }

    public Handler mHandler=new Handler(){
        public void handleMessage(Message msg) {

            if ((mApp._04.get(190)&0x0001)==0x0001){  master.setBackground(green); }else { master.setBackground(gray); };
        }
    };

    protected void onResume(){
        super.onResume();
        MsgSocket instance = MsgSocket.getInstance();
        instance.setParameters(adrr,6,mHandler);
        if (!MsgSocket.flag){
            instance.start();
        }
    }

    protected void onPause(){
        super.onPause();
    }
}