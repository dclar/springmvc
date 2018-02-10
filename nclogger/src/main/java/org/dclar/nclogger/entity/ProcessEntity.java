package org.dclar.nclogger.entity;

public class ProcessEntity {

    String time;
    String host;
    String pid;
    String tinfo;
    String oinfo;
    String msg;

    public ProcessEntity() {

    }

    public ProcessEntity(String time, String host, String pid, String tinfo, String oinfo, String msg) {
        this.time = time;
        this.host = host;
        this.pid = pid;
        this.tinfo = tinfo;
        this.oinfo = oinfo;
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTinfo() {
        return tinfo;
    }

    public void setTinfo(String tinfo) {
        this.tinfo = tinfo;
    }

    public String getOinfo() {
        return oinfo;
    }

    public void setOinfo(String oinfo) {
        this.oinfo = oinfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
