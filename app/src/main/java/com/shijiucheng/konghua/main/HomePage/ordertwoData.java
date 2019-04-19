package com.shijiucheng.konghua.main.HomePage;

public class ordertwoData {

    String id, ddh, status, time, time1, price, address, address1, shrname, shrpho,istixin,statustxt;

    public ordertwoData(String id, String ddh, String status, String time, String time1, String price, String address, String address1, String shrname, String shrpho, String istixin,String statustxt) {
        this.id = id;
        this.ddh = ddh;
        this.status = status;
        this.time = time;
        this.time1 = time1;
        this.price = price;
        this.address = address;
        this.address1 = address1;
        this.shrname = shrname;
        this.shrpho = shrpho;
        this.istixin = istixin;
        this.statustxt = statustxt;
    }

    public String getStatustxt() {
        return statustxt;
    }

    public void setStatustxt(String statustxt) {
        this.statustxt = statustxt;
    }

    public String getIstixin() {
        return istixin;
    }

    public void setIstixin(String istixin) {
        this.istixin = istixin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getShrname() {
        return shrname;
    }

    public void setShrname(String shrname) {
        this.shrname = shrname;
    }

    public String getShrpho() {
        return shrpho;
    }

    public void setShrpho(String shrpho) {
        this.shrpho = shrpho;
    }
}
