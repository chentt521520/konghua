package com.shijiucheng.konghua.main.per.payandget;

public class szmxdata {
    String typestr, num, text, time, status;

    public szmxdata(String typestr, String num, String text, String time, String status) {
        this.typestr = typestr;
        this.num = num;
        this.text = text;
        this.time = time;
        this.status = status;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
