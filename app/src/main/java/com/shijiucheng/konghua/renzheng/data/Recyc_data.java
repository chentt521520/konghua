package com.shijiucheng.konghua.renzheng.data;

public class Recyc_data {
    boolean isselect;
    String txt, id;

    public Recyc_data(boolean isselect, String txt, String id) {
        this.isselect = isselect;
        this.txt = txt;
        this.id = id;
    }

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
