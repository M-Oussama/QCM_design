package com.univ_setif.fsciences.qcm;

/**
 * Created by hzerrad on 17-Mar-18.
 */

public class Question {
    private int questionID;
    private String text;
    private Answer[] answer;

    Question(){
        answer = new Answer[4];
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer[] getAnswerList() {
        return answer;
    }

    public void setAnswers(Answer[] answer) {
        this.answer = answer;
    }

    public void setAnswers(Answer ans1, Answer ans2, Answer ans3, Answer ans4){
        this.answer[0] = ans1;
        this.answer[1] = ans2;
        this.answer[2] = ans3;
        this.answer[3] = ans4;
    }

    @Override
    public String toString(){
        return "Question[" + questionID + ": " + text +"]";
    }
}
