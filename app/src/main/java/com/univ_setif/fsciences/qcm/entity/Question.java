package com.univ_setif.fsciences.qcm.entity;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;

/**
 * Created by hzerrad on 17-Mar-18.
 */

public class Question {
    private String text;
    private Answer correctAnswer;

    public Question(String text, Answer correctAnswer){
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public Question(String text){
        this.text = text;
    }

    public Question(int questionID, String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Answer getAnswer() {
        return correctAnswer;
    }

    public void setAnswer(Answer answer) {
        this.correctAnswer = answer;
    }




    @Override
    public String toString(){
        return "Question[" + text +"]";
    }
}
