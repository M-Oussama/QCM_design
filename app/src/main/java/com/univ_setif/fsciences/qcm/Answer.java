package com.univ_setif.fsciences.qcm;

/**
 * Created by hzerrad on 17-Mar-18.
 */

class Answer {

    private int questionID;
    private int answerID;
    private String text;
    private boolean isCorrect;


    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString(){
        return "Answer[" + questionID + "-" + answerID + ": " + text + "].";
    }
}
