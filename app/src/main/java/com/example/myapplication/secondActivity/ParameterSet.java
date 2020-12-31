package com.example.myapplication.secondActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;

public class ParameterSet extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_set);
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("电气设置");
        ImageView viewById = (ImageView)findViewById(R.id.iv_back);
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(this);
    }
}