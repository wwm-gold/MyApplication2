package com.example.myapplication.util;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MsgSocket implements Runnable {
    public static int socket_PORT = 1001;
    public static String socket_IP = "192.168.4.1";
    private InputStream mReader=null;
    private OutputStream mWriter=null;
    private static MsgSocket mskt=null;
    private  Socket socket=null;
    private static Thread thread=null;
    int [][]addr =null;
    int event;
    public static boolean flag=false;
    Handler handler;
    private MsgSocket(){}
    public static MsgSocket getInstance(){
        if (mskt==null){
            mskt = new MsgSocket();
        }

        return mskt;
    }
    public void start(){
        if (thread!=null){
            thread.interrupt();
            thread=null;
        }
        if (thread==null){
            thread=new Thread(mskt);
            thread.start();
        }
    }
    public void connect(){
        socket=new Socket();
        Log.e("msg","开始建立连接");
        try {
            if (!socket.isConnected()) {
                socket.connect(new InetSocketAddress(socket_IP, socket_PORT),30000);//建立连接
                socket.setSoTimeout(3000);
                mReader=socket.getInputStream();//输入流
                mWriter=socket.getOutputStream();//输出流
                if(socket.isConnected()){
                    flag=true;
                }else flag=false;
                //askDataThread.getPt();
                //Looper.prepare();
                //Looper.loop();//线程池
                //socket.close();
                Log.e("msg","建立完成");
            }else {
                Log.e("msg","重建立连接");
            }

        }catch (Exception e){
            flag=false;
            Log.e("msg","连接服务器异常");
            closeall();
            e.printStackTrace();
        }
    }
    public void setParameters(int[][] addr, int enent, Handler handler){
        this.addr=addr;
        this.event=enent;
        this.handler=handler;
    }
    boolean setFlag=false;//参数设置标志
    //参数设置报文生成
    public void initWriteRequest(int ref,int count,byte[] data,int event,boolean writeFlag){
        setFlag=writeFlag;
        byte refH=(byte)((ref>>8)&0xff);
        byte refL=(byte)(ref&0xff);
        byte countH=(byte)((count>>8)&0xff);
        byte countL=(byte)(count&0xff);
        byte evH=(byte)((event>>8)&0xff);
        byte evL=(byte)(event&0xff);
        byte len=(byte)(data.length&0xff);
        byte[] temp=new byte[data.length+13];
        int i = (data[0] & 0x00ff << 8) | data[1] & 0x00ff;
        byte tlenH=(byte)((temp.length-4>>8)&0xff);
        byte tlenL=(byte)((temp.length-4)&0xff);
//        if (count==1){
//            bytes = new byte[]{evH,evL,0x00,0x00,0x00,0x08,0x01,0x06, refH, refL,data[0],data[1]};
//        }else {
        byte[] bytes2 = new byte[]{evH,evL,0x00,0x00,tlenH,tlenL,0x01,0x10, refH, refL,countH,countL,len};
        System.arraycopy(bytes2,0,temp,0,bytes2.length);
        System.arraycopy(data,0,temp,bytes2.length,data.length);
        bytes=temp;

        //}

    }

    @Override
    public void run() {
        if (!flag){
            connect();
        }

        Modbus mod= new Modbus();
       while (flag){
           if (setFlag){
               byte[] crc2 = mod.getCRC2(bytes);

               sendmsg(crc2);
//               for (int i=0;i<crc2.length;i++){
//                   Log.e("data111", "______"+crc2[i]);
//               }
               Recives(1);

               setFlag=false;
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }else {
               mod.paseSend(addr,event,handler);
           }


       }
    }
    byte[] bytes;
    public void sendmsg(byte[]byt){
        if (flag){
            try {
                mWriter.write(byt);
                mWriter.flush();
               // Log.e("msg","发送信息");
            } catch (Exception e) {
                Log.e("msg","发送异常");
                closeall();
            }
        }
    }

    //定义消息接收子线程，让APP从后台接收消息
    public  byte[] Recives (int num){
        byte[] recive=new byte[256];

        if (flag){
          //  Log.e("msg","开始接收信息");
//            int c=-1;
//
//            int[] read=new int[8];
            try {
                 if (mReader.read(recive,0,recive.length)!=-1){
                     return recive;
                 }
            } catch (IOException e) {
                Log.e("msg","接收异常");
            }

        }
       // Log.e("data", ""+b.toString());
        return recive;
    }
    public void closeall(){

        try {
            if (mReader!=null){
                mReader.close();
                mReader=null;
            }
            if (mWriter!=null){
                mWriter.close();
                mWriter=null;
            }
            if (socket!=null){
                flag=false;
                socket.close();
                socket=null;
            }
            if (thread==null){
                thread.interrupt();
                thread=null;
            }
        } catch (Exception e) {
            Log.e("msg","连接关闭error");
        }

    }
}
