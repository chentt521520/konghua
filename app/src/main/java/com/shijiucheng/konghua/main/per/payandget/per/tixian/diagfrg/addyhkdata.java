package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

public class addyhkdata {
    String id, text,type;//type来区分省市区
    public  addyhkdata(String id, String text,String type){
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
