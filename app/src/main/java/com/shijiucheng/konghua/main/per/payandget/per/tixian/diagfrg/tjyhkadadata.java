package com.shijiucheng.konghua.main.per.payandget.per.tixian.diagfrg;

public class tjyhkadadata {
    String id, icon, bankname, bankNum;

    public tjyhkadadata(String id, String icon, String bankname, String bankNum) {
        this.id = id;
        this.icon = icon;
        this.bankname = bankname;
        this.bankNum = bankNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }
}
