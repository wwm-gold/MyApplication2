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

public class RealDataActivity extends BaseActivity {
    //本页的数据寄存器地址，0位为功能码；从1位开始必须从小到大排列；
    int[] _04=new int[]{4,0x100,0x101,0x102,0x103,0x104,0x105,0x106,0x107,0x108,0x109,0x10a,0x10b,
            0x10c,0x10d,0x10e,0x10f,0x110,0x111,0x112,0x113,0x114,0x115,0x116,0x117,0x15f,0x160,0x161,0x162,0x163,0x164};
    int[][] adrr={_04};
    MyApplication mApp= MyApplication.getInstance();

    TextView DATV15f,DATV160,DATV161,DATV162,DATV163,DATV164;
    TextView DATV100,DATV102,DATV104,DATV106,DATV108,DATV10A,DATV10C,DATV10E,DATV110,DATV112,DATV114,DATV116;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_data);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("实时数据");
        ImageView viewById = findViewById(R.id.iv_back);
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(this);
        DATV15f=findViewById(R.id.DATV0x15f);
        DATV160=findViewById(R.id.DATV0x160);
        DATV161=findViewById(R.id.DATV0x161);
        DATV162=findViewById(R.id.DATV0x162);
        DATV163=findViewById(R.id.DATV0x163);
        DATV164=findViewById(R.id.DATV0x164);
        DATV100=findViewById(R.id.DATV0x100);
        DATV102=findViewById(R.id.DATV0x102);
        DATV104=findViewById(R.id.DATV0x104);
        DATV106=findViewById(R.id.DATV0x106);
        DATV108=findViewById(R.id.DATV0x108);
        DATV10A=findViewById(R.id.DATV0x10a);
        DATV10C=findViewById(R.id.DATV0x10c);
        DATV10E=findViewById(R.id.DATV0x10e);
        DATV110=findViewById(R.id.DATV0x110);
        DATV112=findViewById(R.id.DATV0x112);
        DATV114=findViewById(R.id.DATV0x114);
        DATV116=findViewById(R.id.DATV0x116);

    }
    public Handler mHandler =new Handler(){

        public void handleMessage(Message msg) {

            DATV15f.setText(String.format("%.1f",mApp._04.get(0x15f)/10f));
            DATV160.setText(String.format("%.1f",mApp._04.get(0x160)/10f));
            DATV161.setText(String.format("%.1f",mApp._04.get(0x161)/10f));
            DATV162.setText(String.format("%.1f",mApp._04.get(0x162)/1000f));
            DATV163.setText(String.format("%.1f",mApp._04.get(0x163)/1000f));
            DATV164.setText(String.format("%.1f",mApp._04.get(0x164)/1000f));
            DATV100.setText(String.format("%.1f",mApp._04.get(0x100)/10000f));
            DATV102.setText(String.format("%.1f",mApp._04.get(0x102)/10000f));
            DATV104.setText(String.format("%.1f",mApp._04.get(0x104)/10000f));
            DATV106.setText(String.format("%.1f",mApp._04.get(0x106)/10000f));
            DATV108.setText(String.format("%.1f",mApp._04.get(0x108)/10000f));
            DATV10A.setText(String.format("%.1f",mApp._04.get(0x10a)/10000f));
            DATV10C.setText(String.format("%.1f",mApp._04.get(0x10c)/10000f));
            DATV10E.setText(String.format("%.1f",mApp._04.get(0x10e)/10000f));
            DATV110.setText(String.format("%.1f",mApp._04.get(0x110)/10000f));
            DATV112.setText(String.format("%.1f",mApp._04.get(0x112)/10000f));
            DATV114.setText(String.format("%.1f",mApp._04.get(0x114)/10000f));
            DATV116.setText(String.format("%.1f",mApp._04.get(0x116)/10000f));

        }
    };

    protected void onResume(){
        super.onResume();
        MsgSocket instance = MsgSocket.getInstance();
        instance.setParameters(adrr,2,mHandler);
        if (!MsgSocket.flag){
            instance.start();
        }
    }
    protected void onPause(){
        super.onPause();
    }

}