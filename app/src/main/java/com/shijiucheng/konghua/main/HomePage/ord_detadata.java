package com.shijiucheng.konghua.main.HomePage;

public class ord_detadata {
    //订单详情商品itemdata
    String id, url, huacai, baozhuang,num,url1;

    public ord_detadata(String id, String url, String huacai, String baozhuang,String num,String url1) {
        this.id = id;
        this.url = url;
        this.huacai = huacai;
        this.baozhuang = baozhuang;
        this.num = num;
        this.url1 = url1;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHuacai() {
        return huacai;
    }

    public void setHuacai(String huacai) {
        this.huacai = huacai;
    }

    public String getBaozhuang() {
        return baozhuang;
    }

    public void setBaozhuang(String baozhuang) {
        this.baozhuang = baozhuang;
    }
}
