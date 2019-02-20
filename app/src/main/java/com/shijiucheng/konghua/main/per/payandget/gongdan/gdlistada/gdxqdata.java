package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

public class gdxqdata {
    String reply_type,time,content, imgs;
    public gdxqdata(String reply_type,String time,String content, String imgs){
        this.reply_type = reply_type;
        this.time = time;
        this.content = content;
        this.imgs = imgs;
    }

    public String getReply_type() {
        return reply_type;
    }

    public void setReply_type(String reply_type) {
        this.reply_type = reply_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
