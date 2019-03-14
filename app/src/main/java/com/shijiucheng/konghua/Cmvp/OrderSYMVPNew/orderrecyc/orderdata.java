package com.shijiucheng.konghua.Cmvp.OrderSYMVPNew.orderrecyc;

public class orderdata {
    String orderid, orderstr;
    public orderdata(String orderid,String orderstr){
        this.orderid = orderid;
        this.orderstr = orderstr;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderstr() {
        return orderstr;
    }

    public void setOrderstr(String orderstr) {
        this.orderstr = orderstr;
    }
}
