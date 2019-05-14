package com.shijiucheng.konghua.renzheng.data;

public class huadiandata {
    String name, juli, dizhi, jwd,isselsect;

    public huadiandata(String name, String juli, String dizhi, String jwd,String isselsect) {
        this.name = name;
        this.juli = juli;
        this.dizhi = dizhi;
        this.jwd = jwd;
        this.isselsect = isselsect;
    }

    public String getIsselsect() {
        return isselsect;
    }

    public void setIsselsect(String isselsect) {
        this.isselsect = isselsect;
    }

    public String getJwd() {
        return jwd;
    }

    public void setJwd(String jwd) {
        this.jwd = jwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
}
