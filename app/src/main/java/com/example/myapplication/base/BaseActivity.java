package com.example.myapplication.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public abstract class BaseActivity  extends AppCompatActivity implements View.OnClickListener {
    private MyApplication application;
    private BaseActivity oContext;
    private static volatile Activity mCurrentActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        if (application == null) {
            // 得到Application对象
            application = (MyApplication)getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法

    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }
    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void show_Toast(String text) {
        Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    protected void onResume(){
        super.onResume();
        setCurrentActivity(this);
    }
    protected void onPause(){
        super.onPause();
    }
    private void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }

    protected void onRefresh() {

    }

//    //选择事件处理方法其中position代表选择的序号
//     public abstract void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://通用的返回按钮
                Toast.makeText(this, "点击了返回", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }
    }

//    protected void saveToGallery(Chart chart, String name) {
//        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
//            Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
//                    Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
//                    .show();
//    }
}
