package com.example.libraapplication.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseModel implements Serializable {
    private String party1;
    private String party2;
    private String status;
    private String caseNo;
    private String courtName;
    private String hearingDate;
    private String judgeName;
    private String lawyer;
    private String team;
    private ArrayList<String> EuidList;

    public ArrayList<String> getEuidList() {
        return EuidList;
    }

    public void setEuidList(ArrayList<String> euidList) {
        EuidList = euidList;
    }

    public List<HearingModel> getHearings() {
        return hearings;
    }

    private List<HearingModel> hearings;


    public CaseModel() {

    }

    public String getParty1() {
        return party1;
    }

    public void setParty1(String party1) {
        this.party1 = party1;
    }

    public String getParty2() {
        return party2;
    }

    public void setParty2(String party2) {
        this.party2 = party2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(String hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public List<HearingModel> getHearingModels() {
        return hearings;
    }

    public void setHearings(List<HearingModel> hearings) {
        this.hearings = hearings;
    }
}
