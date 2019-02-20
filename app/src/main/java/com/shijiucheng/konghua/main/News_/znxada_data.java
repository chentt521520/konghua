package com.shijiucheng.konghua.main.News_;

public class znxada_data {
    String status;
    String tit;
    String time;
    String txt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;//type表示是站内信还是公告 0和1

    public znxada_data(String status, String tit, String time, String txt, String id,String type) {
        this.status = status;
        this.tit = tit;
        this.time = time;
        this.txt = txt;
        this.id = id;
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
