package com.mobiledev183.lawapp.Model;

public class AppointmentModel {
    private String title;
    private String desc;
    private String date;
    private String clientName;
    private String clientMbNo;
    private String clientEmail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientMbNo() {
        return clientMbNo;
    }

    public void setClientMbNo(String clientMbNo) {
        this.clientMbNo = clientMbNo;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
