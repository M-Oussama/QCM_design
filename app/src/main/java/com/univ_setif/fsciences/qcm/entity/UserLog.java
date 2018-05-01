package com.univ_setif.fsciences.qcm.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UserLog implements Serializable {
    private String date;
    private Double note;
    private String elapsedTime;
    private int nbrQCM;
    private ArrayList<QCM> qcmList;
    private ArrayList[] answers;

    public UserLog(){

    }

    public UserLog(String date, Double note, String elapsedTime, int nbrQCM, ArrayList<QCM> qcmList, ArrayList[] answers) {
        this.date = date;
        this.note = note;
        this.elapsedTime = elapsedTime;
        this.nbrQCM = nbrQCM;
        this.qcmList = qcmList;
        this.answers = answers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getNbrQCM() {
        return nbrQCM;
    }

    public void setNbrQCM(int nbrQCM) {
        this.nbrQCM = nbrQCM;
    }

    public ArrayList<QCM> getQcmList() {
        return qcmList;
    }

    public void setQcmList(ArrayList<QCM> qcmList) {
        this.qcmList = qcmList;
    }

    public ArrayList[] getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList[] answers) {
        this.answers = answers;
    }

    @Override
    public String toString(){
        return "UserLog[Date: " + date + ", Mark: " + note + ", Elapsed: " + elapsedTime + "]";
    }
}
