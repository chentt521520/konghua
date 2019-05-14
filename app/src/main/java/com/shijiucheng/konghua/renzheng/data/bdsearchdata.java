package com.shijiucheng.konghua.renzheng.data;

public class bdsearchdata {
    String txt, jwd,city;
    public  bdsearchdata(String txt,String jwd,String city){
        this.txt = txt;
        this.city = city;
        this.jwd = jwd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getJwd() {
        return jwd;
    }

    public void setJwd(String jwd) {
        this.jwd = jwd;
    }
}
