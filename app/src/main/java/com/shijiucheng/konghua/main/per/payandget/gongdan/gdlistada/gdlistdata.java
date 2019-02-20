package com.shijiucheng.konghua.main.per.payandget.gongdan.gdlistada;

public class gdlistdata {
    String Id, Time, Content, TypeStr, Status, Status1;

    public gdlistdata(String Id, String Time, String Content, String TypeStr, String Status, String status1) {
        this.Id = Id;
        this.Time = Time;
        this.Content = Content;
        this.TypeStr = TypeStr;
        this.Status = Status;
        this.Status1 = status1;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getStatus1() {
        return Status1;
    }

    public void setStatus1(String status1) {
        Status1 = status1;
    }

    public String getTypeStr() {
        return TypeStr;
    }

    public void setTypeStr(String typeStr) {
        TypeStr = typeStr;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
