package com.example.myapplication.register;

public class Register {
    private int type;//01 02 03 04 05 06 16
    private int valueType;//str int bit float
    private int address;
    private int length;
    private int value;
    private byte[] bytes=null;
    private String describe;
    private int coe;

    public Register(int type,int valueType,int address,int length,String describe,int coe){
        this.type=type;
        this.valueType=valueType;
        this.address=address;
        this.value=0;
        this.length=length;
        this.describe=describe;
        this.coe=coe;
    }
    //public Register(){}
    public Register(int type,int address){
        this.type=type;
        this.valueType=1;
        this.address=address;
        this.value=0;
        this.length=1;
        this.describe="";
    }
    public void saveRegister(String data){


    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getCoe() {
        return coe;
    }

    public void setCoe(int coe) {
        this.coe = coe;
    }
}
