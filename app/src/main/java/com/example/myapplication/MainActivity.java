package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.View;

import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.MyApplication;
import com.example.myapplication.secondActivity.CommunicationStatus;
import com.example.myapplication.secondActivity.FaultInfo;
import com.example.myapplication.secondActivity.Mqttset;
import com.example.myapplication.secondActivity.ParameterSet;
import com.example.myapplication.secondActivity.PowerQuality;
import com.example.myapplication.secondActivity.RealDataActivity;
import com.example.myapplication.secondActivity.SlaveSet;
import com.example.myapplication.secondActivity.TimeSet;
import com.example.myapplication.secondActivity.WifiSet;
import com.example.myapplication.util.MsgSocket;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    int[] _04=new int[]{4,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,
            22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,
            59,60,61,62,63,64,65,66,67,68,69,70,71,72};
    int[][] adrr={_04};
    MyApplication mApp=MyApplication.getInstance();
    SparseIntArray m_04=mApp._04;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.real_data).setOnClickListener(this);
        findViewById(R.id.powerQuality).setOnClickListener(this);
        findViewById(R.id.communicationStatus).setOnClickListener(this);
        findViewById(R.id.faultInfo).setOnClickListener(this);
        findViewById(R.id.BaseEleSet).setOnClickListener(this);
        findViewById(R.id.wifiSet).setOnClickListener(this);
        findViewById(R.id.mqttset).setOnClickListener(this);
        findViewById(R.id.timeset).setOnClickListener(this);
        findViewById(R.id.slaveNameset).setOnClickListener(this);
        Log.e("msg","start");
        timeTask();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.real_data://进入实时数据界面
                Intent intent = new Intent(getCurrentActivity(), RealDataActivity.class);
                startActivity(intent);
                break;
            case R.id.powerQuality://电能质量界面
                startActivity(new Intent(getCurrentActivity(), PowerQuality.class));
                break;
            case R.id.communicationStatus://连接状态界面
                startActivity(new Intent(getCurrentActivity(), CommunicationStatus.class));
                break;
            case R.id.faultInfo://故障界面
                startActivity(new Intent(getCurrentActivity(), FaultInfo.class));
                break;
            case R.id.BaseEleSet://电气设置
                startActivity(new Intent(getCurrentActivity(), ParameterSet.class));
                break;
            case R.id.wifiSet://电气设置
                startActivity(new Intent(getCurrentActivity(), WifiSet.class));
                break;
            case R.id.mqttset://电气设置
                startActivity(new Intent(getCurrentActivity(), Mqttset.class));
                break;
            case R.id.timeset://电气设置
                startActivity(new Intent(getCurrentActivity(), TimeSet.class));
                break;
            case R.id.slaveNameset://电气设置
                startActivity(new Intent(getCurrentActivity(), SlaveSet.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MsgSocket instance = MsgSocket.getInstance();
        instance.setParameters(adrr,01,mHandler);
        if (!MsgSocket.flag){
            instance.start();
        }
    }
    public Handler mHandler =new Handler(){

        public void handleMessage(Message msg){//更新实时数据


        }
    };
    Timer timer=null;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //判断当点击的是返回键
        if (keyCode == event.KEYCODE_BACK) {
            exit();//退出方法
        }
        return true;
    }

    public void timeTask(){

        timer=new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message MSG=new Message();
                if (MsgSocket.flag){
                    MSG.what=1;
                }else {
                    MSG.what=0;
                    MsgSocket instance = MsgSocket.getInstance();
                    instance.start();
                }


            }
        },3000,5000);
    }
    //退出方法
    long time;
    private void exit() {
//如果在两秒大于2秒
        if (System.currentTimeMillis() - time > 2000) {//获得当前的时间
            time= System.currentTimeMillis();
            show_Toast("再点击一次退出应用程序");
        } else {
//点击在两秒以内
            timer.cancel();//取消定时任务
            MsgSocket.getInstance().closeall();
            removeALLActivity();//执行移除所以Activity方法
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}