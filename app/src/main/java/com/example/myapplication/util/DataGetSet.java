package com.example.myapplication.util;

import android.util.ArrayMap;
import android.util.SparseArray;

import com.example.myapplication.base.MyApplication;

import java.util.Arrays;

/**
 * Created by wwm on 2020/4/7.
 */

public class DataGetSet{
    MyApplication mApp= MyApplication.getInstance();
   // MyModbusFactory mmf=new MyModbusFactory();
    public void paseSend(int[] addr,byte by,byte fun){//轮询数据
        Arrays.sort(addr);//进行排序
        if (addr.length>1){
            int count=0;//寄存器个数
            for(int i = 0; i< addr.length-1; i++){//
                if (addr[i]== addr[i+1]-1){//找出相邻寄存器个数
                    count++;
                }else if(count>0){//发送本次轮询数据
                    int addrH = (addr[i-count+1] & 0xff00) >> 8;
                    int addrL= addr[i-count+1]&0xff;
                    int len=count+1;
                    int lenH=(len&0xff00)>>8;
                    int lenL=len&0xff;
                    //mmf.setReq(addr[i-count+1],len,fun);
//                    byte[] b=new byte[]{0x00,by,0x00,0x00,0x00,0x08,0x01,fun,(byte) addrH,(byte) addrL,(byte)lenH,(byte)lenL };
//                    Modbus.getCRC2(b);
                    count=0;
                }else if (count==0){
                    int addrH = (addr[i] & 0xff00) >> 8;
                    int addrL= addr[i]&0xff;
                    int lenH=0;
                    int lenL=1;
                   // mmf.setReq(addr[i],1,fun);
//                    byte[] b=new byte[]{0x00,by,0x00,0x00,0x00,0x08,0x01,fun,(byte) addrH,(byte) addrL,(byte)lenH,(byte)lenL };
//                    Modbus.getCRC2(b);
                }
            }
        }else {
            int addrH = (addr[0] & 0xff00) >> 8;
            int addrL= addr[0]&0xff;
            int lenH=0;
            int lenL=1;
           // mmf.setReq(addr[0],1,fun);
//            byte[] b=new byte[]{0x00,by,0x00,0x00,0x00,0x08,0x01,fun,(byte) addrH,(byte) addrL,(byte)lenH,(byte)lenL };
//            Modbus.getCRC2(b);
        }
    }
    public void init_03Map(int[] reg){//初始化全局变量
        for (int i=0;i<reg.length;i++){
            mApp._03.append(reg[i],0);
        }
    }
    public void init_04Map(int[] reg){//初始化全局变量
        for (int i=0;i<reg.length;i++){
            mApp._04.append(reg[i],0);
        }
    }
    public void setSend(int[] addr,byte by,int[] data){//轮询数据
        Arrays.sort(addr);//进行排序
        int addrH = (addr[0] & 0xff00) >> 8;
        int addrL= addr[0]&0xff;
        int lenH=(addr.length&0xff00)>>8;
        int lenL=addr.length&0xff;
        byte[] b=new byte[12+(data.length*2)];
        b[0]=0x00;b[1]=by;b[2]=0x00;b[6]=0x01;b[7]=0x10;b[8]=(byte)addrH;b[9]=(byte)addrL;b[10]=(byte) lenH;b[11]=(byte)lenL;
        int count=0;
        for(int i = 0; i< data.length; i++){//
            int dataH = (data[i] & 0xff00) >> 8;
            int dataL = data[i] & 0xff;
            b[count+12]=(byte)dataH;
            b[count+13]=(byte)dataL;
            count+=2;
        }
       // Modbus.getCRC2(b);
    }
    public void setSend(int addr,byte by,int data){//轮询数据
        int addrH = (addr & 0xff00) >> 8;
        int addrL= addr&0xff;
        int dataH = (data & 0xff00) >> 8;
        int dataL= data&0xff;
        byte[] b=new byte[]{0x00,by,0x00,0x00,0x00,0x08,0x01,0x06,(byte) addrH,(byte) addrL,(byte)dataH,(byte)dataL };
       // Modbus.getCRC2(b);
    }
    SparseArray<Integer> _03=new SparseArray<>();
    SparseArray<String> name_03=new SparseArray<>();
    SparseArray<Integer> _04=new SparseArray<>();
    SparseArray<String> name_04=new SparseArray<>();
    final int[] addr=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41};
    public void dataInit04(int[] data04){
        for (int i=0;i<data04.length;i++){
            _04.put(i,data04[i]);
        }
    }
    public void dataInit03(int[] data03){
        for (int i=0;i<data03.length;i++){
            _04.put(i,data03[i]);
        }
    }
    public void set_03(ArrayMap<Integer,Integer> arrmap) {


    }

    public SparseArray get_03() {
        return _03;
    }

    public SparseArray get_04() {
        return _04;
    }

    public void set_04(SparseArray _04) {
        this._04 = _04;
    }
    public void setName03(){
        name_03.put(0,"启动开关");
        name_03.put(1,"故障复位");
        name_03.put(2,"互感器位置");
        name_03.put(3,"补偿功能模式设置");
        name_03.put(4,"感容选择");
        name_03.put(5,"从机通讯地址");
        name_03.put(6,"CT变比");
        name_03.put(7,"并机台数");
        name_03.put(8,"目标功率因数");
        name_03.put(9,"不平衡、无功开启开关");
        name_03.put(10,"2～25次谐波开启开关");
        name_03.put(11,"25～50次谐波开启开关");
        name_03.put(12,"负序电流百分比");
        name_03.put(13,"零序电流百分比");
        name_03.put(14,"无功电流百分比");
        name_03.put(15,"按功率因数设定百分比");
        name_03.put(16,"无功定向输出百分比");
        name_03.put(17,"2次百分比");
        name_03.put(18,"3次百分比");
        name_03.put(19,"4次百分比");
        name_03.put(20,"5次百分比");
        name_03.put(21,"7次百分比");
        name_03.put(22,"9次百分比");
        name_03.put(23,"11次百分比");
        name_03.put(24,"13次百分比");
        name_03.put(25,"15次百分比");
        name_03.put(26,"17次百分比");
        name_03.put(27,"19次百分比");
        name_03.put(28,"21次百分比");
        name_03.put(29,"23次百分比");
        name_03.put(30,"25次百分比");
        name_03.put(31,"29次百分比");
        name_03.put(32,"31次百分比");
        name_03.put(33,"35次百分比");
        name_03.put(34,"37次百分比");
        name_03.put(35,"41次百分比");
        name_03.put(36,"43次百分比");
        name_03.put(37,"47次百分比");
        name_03.put(38,"49次百分比");
        name_03.put(39,"无源工作模式");
        name_03.put(40,"共补电容个数");
        name_03.put(41,"切除延时时间");
        name_03.put(42,"投入延时时间");
        name_03.put(43,"分补电容个数");
        name_03.put(44,"共补最小容量");
        name_03.put(45,"分补最小容量");
        name_03.put(46,"共补编码方式");
        name_03.put(47,"切除容量");
        name_03.put(48,"手动投切按钮");
        name_03.put(49,"分补编码方式");
        name_03.put(50,"过压门限");
        name_03.put(51,"欠压门限");
        name_03.put(52,"电压给定");
        name_03.put(53,"畸变门限");
        name_03.put(54,"电网过压设定值");
        name_03.put(55,"UAC欠压阈值");
        name_03.put(56,"UDC过压阈值");
        name_03.put(57,"UDC欠压阈值");
        name_03.put(58,"UDC门槛");
        name_03.put(59,"D轴电压保护阈值");
        name_03.put(60,"UAC之和保护阈值");
        name_03.put(61,"总限流");
        name_03.put(62,"过流设定");
        name_03.put(63,"门槛电流");
        name_03.put(64,"无功电流给定");
        name_03.put(65,"无功限流");
        name_03.put(66,"不平衡门槛");
        name_03.put(67,"不平衡限流");
        name_03.put(68,"预留");
        name_03.put(69,"谐波限流");
        name_03.put(70,"分流标定");
        name_03.put(71,"电容过流");
        name_03.put(72,"电感过温门限");
        name_03.put(73,"IGBT过温门限");
        name_03.put(74,"电容过温");
        name_03.put(75,"环境过温");
        name_03.put(76,"485A1B1回传数据延时");
        name_03.put(77,"485A1B1通讯波特率");
        name_03.put(78,"485A2B2回传数据延时");
        name_03.put(79,"485A2B2通讯波特率");
        name_03.put(80,"主从设置");
        name_03.put(81,"wifi名称1");
        name_03.put(82,"wifi名称2");
        name_03.put(83,"wifi名称3");
        name_03.put(84,"wifi名称4");
        name_03.put(85,"wifi名称5");
        name_03.put(86,"wifi名称5");
        name_03.put(87,"密码1");
        name_03.put(88,"密码2");
        name_03.put(89,"计数次数");
        name_03.put(90,"外接485A口通讯地址");
        name_03.put(91,"外接485C口通讯地址");
        name_03.put(92,"外接485D口通讯地址");
        name_03.put(93,"2次限流");
        name_03.put(94,"3次限流");
        name_03.put(95,"4次限流");
        name_03.put(96,"5次限流");
        name_03.put(97,"7次限流");
        name_03.put(98,"9次限流");
        name_03.put(99,"11次限流");
        name_03.put(100,"13次限流");
        name_03.put(101,"15次限流");
        name_03.put(102,"17次限流");
        name_03.put(103,"19次限流");
        name_03.put(104,"21次限流");
        name_03.put(105,"23次限流");
        name_03.put(106,"25次限流");
        name_03.put(107,"29次限流");
        name_03.put(108,"31次限流");
        name_03.put(109,"35次限流");
        name_03.put(110,"37次限流");
        name_03.put(111,"41次限流");
        name_03.put(112,"43次限流");
        name_03.put(113,"47次限流");
        name_03.put(114,"49次限流");
        name_03.put(115,"出厂参数恢复");
        name_03.put(116,"不平衡、无功开启开关");
        name_03.put(117,"2～25次谐波开启开关");
        name_03.put(118,"25～50次谐波开启开关");
        name_03.put(119,"控制开关");
        name_03.put(120,"霍尔方向");
        name_03.put(121,"电流比例");
        name_03.put(122,"比例系数1");
        name_03.put(123,"比例系数2");
        name_03.put(124,"比例系数3");
        name_03.put(125,"比例系数4");
        name_03.put(126,"比例系数5");
        name_03.put(127,"谐波增益系数1");
        name_03.put(128,"谐波增益系数2");
        name_03.put(129,"谐波Kr1");
        name_03.put(130,"谐波Kr2");
        name_03.put(131,"电压平衡比例系数");
        name_03.put(132,"电压平衡积分系数");
        name_03.put(133,"有源阻尼系数K1");
        name_03.put(134,"UA校准系数");
        name_03.put(135,"UB校准系数");
        name_03.put(136,"UC校准系数");
        name_03.put(137,"IA校准系数");
        name_03.put(138,"IB校准系数");
        name_03.put(139,"IC校准系数");
        name_03.put(140,"IA1校准系数");
        name_03.put(141,"IB1校准系数");
        name_03.put(142,"IC1校准系数");
        name_03.put(143,"IA2校准系数");
        name_03.put(144,"IB2校准系数");
        name_03.put(145,"IC2校准系数");
        name_03.put(146,"Udc正校准系数");
        name_03.put(147,"Udc负校准系数");
        name_03.put(148,"PI限幅值1");
        name_03.put(149,"PI限幅值2");
        name_03.put(150,"CF限幅值1");
        name_03.put(151,"CF限幅值2");
        name_03.put(152,"锁相P");
        name_03.put(153,"锁相I");
        name_03.put(154,"THD调整");
        name_03.put(155,"接线制形式");
        name_03.put(156,"切换标志位");
    }
    public void setName04(){
        name_04.put(0,"开启标志");
        name_04.put(1,"实时保护");
        name_04.put(2,"直流母线上侧电压");
        name_04.put(3,"直流母线下侧电压");
        name_04.put(4,"直流母线总电压");
        name_04.put(5,"设备A相输出电流");
        name_04.put(6,"设备B相输出电流");
        name_04.put(7,"设备C相输出电流");
        name_04.put(8,"设备N输出电流");
        name_04.put(9,"网侧电流IA");
        name_04.put(10,"网侧电流IB");
        name_04.put(11,"网侧电流IC");
        name_04.put(12,"网侧电流IN");
        name_04.put(13,"负载电流IA");
        name_04.put(14,"负载电流IB");
        name_04.put(15,"负载电流IC");
        name_04.put(16,"负载电流IN");
        name_04.put(17,"A相电压");
        name_04.put(18,"B相电压");
        name_04.put(19,"C相电压");
        name_04.put(20,"电网频率");
        name_04.put(21,"A相视在功率");
        name_04.put(22,"B相视在功率");
        name_04.put(23,"C相视在功率");
        name_04.put(24,"系统总视在功率");
        name_04.put(25,"A相有功功率");
        name_04.put(26,"B相有功功率");
        name_04.put(27,"C相有功功率");
        name_04.put(28,"系统总有功功率");
        name_04.put(29,"A相无功功率");
        name_04.put(30,"B相无功功率");
        name_04.put(31,"C相无功功率");
        name_04.put(32,"系统总无功功率");
        name_04.put(33,"A相功率因数");
        name_04.put(34,"B相功率因数");
        name_04.put(35,"C相功率因数");
        name_04.put(36,"系统功率因数");
        name_04.put(37,"负载A相视在功率");
        name_04.put(38,"负载B相视在功率");
        name_04.put(39,"负载C相视在功率");
        name_04.put(40,"负载总视在功率");
        name_04.put(41,"负载A相有功");
        name_04.put(42,"负载B相有功");
        name_04.put(43,"负载C相有功");
        name_04.put(44,"负载总有功");
        name_04.put(45,"负载A相无功");
        name_04.put(46,"负载B相无功");
        name_04.put(47,"负载C相无功");
        name_04.put(48,"负载总无功");
        name_04.put(49,"负载A相cos");
        name_04.put(50,"负载B相cos");
        name_04.put(51,"负载C相cos");
        name_04.put(52,"负载总cos");
        name_04.put(53,"设备A无功功率");
        name_04.put(54,"设备B无功功率");
        name_04.put(55,"设备C无功功率");
        name_04.put(56,"设备总无功功率");
        name_04.put(57,"设备输出电流Ia1");
        name_04.put(58,"设备输出电流Ib1");
        name_04.put(59,"设备输出电流Ic1");
        name_04.put(60,"设备输出电流In1");
        name_04.put(61,"设备输出电流Ia2");
        name_04.put(62,"设备输出电流Ib2");
        name_04.put(63,"设备输出电流Ic2");
        name_04.put(64,"设备输出电流In2");
        name_04.put(65,"电容滤波支路电流IA");
        name_04.put(66,"电容滤波支路电流IB");
        name_04.put(67,"电容滤波支路电流IC");
        name_04.put(68,"电容滤波支路电流IN");
        name_04.put(69,"负载侧电流不平衡度");
        name_04.put(70,"电网侧电流不平衡度");
        name_04.put(71,"电流谐波畸变率");
        name_04.put(72,"A相IGBT温度1");
        name_04.put(73,"A相IGBT温度2");
        name_04.put(74,"B相IGBT温度1");
        name_04.put(75,"B相IGBT温度2");
        name_04.put(76,"C相IGBT温度1");
        name_04.put(77,"C相IGBT温度2");
        name_04.put(78,"电感温度");
        name_04.put(79,"电感温度");
        name_04.put(80,"电感温度");
        name_04.put(81,"电容温度");
        name_04.put(82,"电容温度");
        name_04.put(83,"模块环境温度");
        name_04.put(84,"模块环境湿度");
        name_04.put(85,"2次谐波电流计算百分比");
        name_04.put(86,"3次谐波电流计算百分比");
        name_04.put(87,"4次谐波电流计算百分比");
        name_04.put(88,"5次谐波电流计算百分比");
        name_04.put(89,"6次谐波电流计算百分比");
        name_04.put(90,"7次谐波电流计算百分比");
        name_04.put(91,"8次谐波电流计算百分比");
        name_04.put(92,"9次谐波电流计算百分比");
        name_04.put(93,"10次谐波电流计算百分比");
        name_04.put(94,"11次谐波电流计算百分比");
        name_04.put(95,"12次谐波电流计算百分比");
        name_04.put(96,"13次谐波电流计算百分比");
        name_04.put(97,"14次谐波电流计算百分比");
        name_04.put(98,"15次谐波电流计算百分比");
        name_04.put(99,"16次谐波电流计算百分比");
        name_04.put(100,"17次谐波电流计算百分比");
        name_04.put(101,"18次谐波电流计算百分比");
        name_04.put(102,"19次谐波电流计算百分比");
        name_04.put(103,"20次谐波电流计算百分比");
        name_04.put(104,"21次谐波电流计算百分比");
        name_04.put(105,"22次谐波电流计算百分比");
        name_04.put(106,"23次谐波电流计算百分比");
        name_04.put(107,"24次谐波电流计算百分比");
        name_04.put(108,"25次谐波电流计算百分比");
        name_04.put(109,"26次谐波电流计算百分比");
        name_04.put(110,"27次谐波电流计算百分比");
        name_04.put(111,"28次谐波电流计算百分比");
        name_04.put(112,"29次谐波电流计算百分比");
        name_04.put(113,"30次谐波电流计算百分比");
        name_04.put(114,"31次谐波电流计算百分比");
        name_04.put(115,"32次谐波电流计算百分比");
        name_04.put(116,"33次谐波电流计算百分比");
        name_04.put(117,"34次谐波电流计算百分比");
        name_04.put(118,"35次谐波电流计算百分比");
        name_04.put(119,"36次谐波电流计算百分比");
        name_04.put(120,"37次谐波电流计算百分比");
        name_04.put(121,"38次谐波电流计算百分比");
        name_04.put(122,"39次谐波电流计算百分比");
        name_04.put(123,"40次谐波电流计算百分比");
        name_04.put(124,"41次谐波电流计算百分比");
        name_04.put(125,"42次谐波电流计算百分比");
        name_04.put(126,"43次谐波电流计算百分比");
        name_04.put(127,"44次谐波电流计算百分比");
        name_04.put(128,"45次谐波电流计算百分比");
        name_04.put(129,"46次谐波电流计算百分比");
        name_04.put(130,"47次谐波电流计算百分比");
        name_04.put(131,"48次谐波电流计算百分比");
        name_04.put(132,"49次谐波电流计算百分比");
        name_04.put(133,"50次谐波电流计算百分比");
        name_04.put(134,"A相电流曲线");
        name_04.put(135,"B相电流曲线");
        name_04.put(136,"C相电流曲线");
        name_04.put(137,"A相电压曲线");
        name_04.put(138,"B相电压曲线");
        name_04.put(139,"C相电压曲线");
        name_04.put(140,"DSP程序版本1");
        name_04.put(141,"DSP程序版本2");
        name_04.put(142,"DSP程序版本3");
        name_04.put(143,"DSP程序版本4");
        name_04.put(144,"DSP程序版本5");
        name_04.put(145,"DSP程序版本6");
        name_04.put(146,"DSP程序版本7");
        name_04.put(147,"DSP程序版本8");
    }
}
