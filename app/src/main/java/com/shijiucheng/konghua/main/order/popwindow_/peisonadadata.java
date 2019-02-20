package com.shijiucheng.konghua.main.order.popwindow_;

public class peisonadadata {
    String str,pho,isselect;
    public peisonadadata (String str,String pho,String isselect){
        this.str = str;
        this.pho=pho;
        this.isselect = isselect;
    }

    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
    }

    public String getPho() {
        return pho;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
