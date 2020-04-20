package com.mobiledev183.lawapp.Model;

public class Timeline {
    String date;
    String room_name;
    String judge_name;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getJudge_name() {
        return judge_name;
    }

    public void setJudge_name(String judge_name) {
        this.judge_name = judge_name;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "date='" + date + '\'' +
                ", room_name='" + room_name + '\'' +
                ", judge_name='" + judge_name + '\'' +
                '}';
    }
}
