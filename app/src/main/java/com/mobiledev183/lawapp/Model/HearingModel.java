package com.mobiledev183.lawapp.Model;

import java.io.Serializable;

public class HearingModel implements Serializable {
    private String judge_name;
    private String hearing_date;
    private String category;
    private String case_no;

    public HearingModel() {
    }


    public String getJudge_name() {
        return judge_name;
    }

    public void setJudge_name(String judge_name) {
        this.judge_name = judge_name;
    }

    public String getHearing_date() {
        return hearing_date;
    }

    public void setHearing_date(String hearing_date) {
        this.hearing_date = hearing_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCase_no() {
        return case_no;
    }

    public void setCase_no(String case_no) {
        this.case_no = case_no;
    }
}
