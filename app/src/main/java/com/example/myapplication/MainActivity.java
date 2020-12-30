package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.secondActivity.CommunicationStatus;
import com.example.myapplication.secondActivity.FaultInfo;
import com.example.myapplication.secondActivity.ParameterSet;
import com.example.myapplication.secondActivity.PowerQuality;
import com.example.myapplication.secondActivity.RealDataActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.real_data).setOnClickListener(this);
        findViewById(R.id.powerQuality).setOnClickListener(this);
        findViewById(R.id.communicationStatus).setOnClickListener(this);
        findViewById(R.id.faultInfo).setOnClickListener(this);
        findViewById(R.id.parameterSet).setOnClickListener(this);
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
            case R.id.parameterSet://参数设置
                startActivity(new Intent(getCurrentActivity(), ParameterSet.class));
                break;
        }
    }
}