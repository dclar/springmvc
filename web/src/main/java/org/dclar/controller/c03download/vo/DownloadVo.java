package org.dclar.controller.c03download.vo;

public class DownloadVo {

    String url;
    String cookieValue;
    String statusValue;

    String filePath_;

    public String getFilePath_() {
        return filePath_;
    }

    public void setFilePath_(String filePath_) {
        this.filePath_ = filePath_;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;

    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}



