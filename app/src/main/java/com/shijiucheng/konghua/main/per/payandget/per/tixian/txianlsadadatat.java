package com.shijiucheng.konghua.main.per.payandget.per.tixian;

public class txianlsadadatat {
    String time, num, bank, statusstr, yuanying;

    public txianlsadadatat(String time, String num, String bank, String statusstr,String yuanying) {
        this.time = time;
        this.num = num;
        this.bank = bank;
        this.statusstr = statusstr;
        this.yuanying = yuanying;
    }

    public String getYuanying() {
        return yuanying;
    }

    public void setYuanying(String yuanying) {
        this.yuanying = yuanying;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStatusstr() {
        return statusstr;
    }

    public void setStatusstr(String statusstr) {
        this.statusstr = statusstr;
    }
}
