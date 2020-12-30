package com.example.myapplication.base;

import android.app.Activity;
import android.app.Application;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    private static MyApplication mApp;
    public SparseIntArray _03=new SparseIntArray();
    public SparseIntArray _04=new SparseIntArray();
    public static  MyApplication getInstance(){
        return mApp;
    }
    public void onCreate() {
        super.onCreate();
        mApp=this;
        oList = new ArrayList<Activity>();
        initdata();
    }
    private void initdata(){
        for (int i=0;i<200;i++){
            _03.put(i,1);
            _04.put(i,1);
        }
    }
    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }
}
