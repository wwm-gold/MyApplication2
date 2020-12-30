package com.example.myapplication.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.base.MyApplication;

import java.util.List;

public class Modbus {
    //CnStat cnStat = new CnStat();
//        ModBus 通信协议的 CRC ( 冗余循环校验码含2个字节, 即 16 位二进制数。
//        CRC 码由发送设备计算, 放置于所发送信息帧的尾部。
//        接收信息设备再重新计算所接收信息 (除 CRC 之外的部分）的 CRC,
//        比较计算得到的 CRC 是否与接收到CRC相符, 如果两者不相符, 则认为数据出错。
//        1) 预置 1 个 16 位的寄存器为十六进制FFFF(即全为 1) , 称此寄存器为 CRC寄存器。
//        2) 把第一个 8 位二进制数据 (通信信息帧的第一个字节) 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器。
//        3) 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位。
//        4) 如果移出位为 0, 重复第 3 步 ( 再次右移一位); 如果移出位为 1, CRC 寄存器与多项式A001 ( 1010 0000 0000 0001) 进行异或。
//        5) 重复步骤 3 和步骤 4, 直到右移 8 次,这样整个8位数据全部进行了处理。
//        6) 重复步骤 2 到步骤 5, 进行通信信息帧下一个字节的处理。
//        7) 将该通信信息帧所有字节按上述步骤计算完成后,得到的16位CRC寄存器的高、低字节进行交换。
//        8) 最后得到的 CRC寄存器内容即为 CRC码。
    public  byte[] getCRC2(byte[] bytes) {

        int length = bytes.length;
        byte[]bytes1=new byte[length+2];
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            bytes1[i]=bytes[i];
            CRC ^=  (bytes[i]&0xff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) == 1) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        bytes1[length]=(byte) (CRC & 0xFF);
        bytes1[length+1]=(byte) ((CRC & 0xFF00) >> 8);
        //高低位转换，看情况使用（譬如本人这次对led彩屏的通讯开发就规定校验码高位在前低位在后，也就不需要转换高低位)
        // CRC = ( (CRC & 0x0000FF00) >> 8) | ( (CRC & 0x000000FF ) << 8);
        return bytes1;

    }
    private byte[]bytes;
    private int[] dataparser(int m){
        int count=1;
        int[]dataset=new int[(m-11)/2+1];
        dataset[0]=bytes[1];
        int j=0;
        for (int i=9;i<m-2;i+=2){
            j=bytes[i]<<8;
            j|=(bytes[i+1]&0x000000ff);
            dataset[count]=j;
            count++;
        }
        return dataset;
    }
    public int mflag=6;//
    private MyApplication mApp= MyApplication.getInstance();
    /**
     * 判断某个activity是否在前台显示
     */
    public  boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    private boolean isForeground(Activity context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
    //接受的信息转为字符串
    public String functionset(int fs){
        String str=new String(bytes);
        String substring = str.substring(10, bytes.length - 2);
        return substring;

    }

    Handler handler;
    public void paseSend(int[] addr, int event, Handler handler){//轮询数据
        this.handler=handler;
        if (addr!=null&&addr.length>=2){
            //Arrays.sort(addr[i]);//进行排序
            if (addr.length > 2) {
                int count = 0;//寄存器个数

                for (int j = 1; j <addr.length; j++) {//
                    if (j == addr.length - 1 && count == 0) {
                        setReq(addr[j], 1, addr[0], event);
                    } else if (j == addr.length - 1 && count != 0) {
                        int len = count + 1;
                        setReq(addr[j - count], len, addr[0], event);
                        count = 0;
                    } else if (addr[j] == addr[j+1]-1) {//找出相邻寄存器个数
                        count++;
                    } else {//发送本次轮询数据
                        int len = count + 1;
                        setReq(addr[j - count], len, addr[0], event);
                        count = 0;
                    }
                }

            } else if (addr.length == 2) {

                setReq(addr[1], 1, addr[0], event);
            } else Log.e("reques", "请求地址错误");
        }else Log.e("adress","轮询地址错误");
    }
    public void paseSend(int[][] addr, int event, Handler handler){//轮询数据
        this.handler=handler;
        int [] tmpe;
        if (addr!=null) {
            for (int i = 0; i < addr.length; i++) {
                tmpe=addr[i];
                paseSend(tmpe,event,handler);

            }
        }else Log.e("adress", "轮询地址错误");
    }
    MsgSocket msk=MsgSocket.getInstance();
    private int x=0;
    private void setReq(int ref,int count,int funCode,int event){
        x++;
        byte refH=(byte)((ref>>8)&0xff);
        byte refL=(byte)(ref&0xff);
        byte countH=(byte)((count>>8)&0xff);
        byte countL=(byte)(count&0xff);

        byte evH=(byte)((x>>8)&0xff);
        byte evL=(byte)(x&0xff);
        byte[] bytes = {evH,evL,0x00,0x00,0x00,0x08,0x01,(byte) funCode, refH, refL,countH,countL};

        byte[] crc2 = getCRC2(bytes);
//        for (int j=0;j<crc2.length;j++){
//            // System.out.println();
//            Log.e("data13", ""+crc2[j]);
//        }
        msk.sendmsg(crc2);
        int[] b = new int[count];
        byte[] recives = msk.Recives(count);
        int ev = (recives[0]&0x00ff) << 8 | (recives[1]&0x00ff);
        int len =( (recives[4]&0x00ff) << 8 )| (recives[5]&0x00ff);
        int fun = recives[7];
        int nolen=count*2+5;
        Log.e("data12", ""+ev+":"+x+","+fun+":"+funCode+","+len+":"+nolen);

        if (funCode==3|funCode==4){
            if (ev==x&&fun==funCode){//&&len==(count*2+5)

                int j;
                int c=0;
                for (int i=9;i<count*2+9;i+=2){
                    j=recives[i]<<8;
                    j|=(recives[i+1]&0x000000ff);
                    b[c]=j;
                    c++;
//                    Log.e("data1", ""+j);
                }
//                Log.e("data2", ""+c);
                switch (funCode){
                    case 3:
                        for (int i=0;i<count;i++){
                            mApp._03.put(ref+i,b[i]);
                        }
                        break;
                    case 4:
                        for (int i=0;i<count;i++){
                            mApp._04.put(ref+i,b[i]);
                        }

                }

                if (x>=1000){
                    x=0;
                }
                boradcast(event);
               // Log.e("data5", msk.setFlag+"1234567890+++++++=");
            }
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void boradcast(int ev){
        Message msg=new Message();
        msg.what=ev;
       // Log.e("order", "handler");
        handler.sendMessage(msg);
    }
}