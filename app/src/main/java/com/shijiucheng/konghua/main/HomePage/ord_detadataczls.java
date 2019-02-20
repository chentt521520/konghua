package com.shijiucheng.konghua.main.HomePage;

public class ord_detadataczls {
    //订单详情操作历史itemdata
    String id, text;

    public ord_detadataczls(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
