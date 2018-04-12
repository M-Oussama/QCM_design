package com.univ_setif.fsciences.qcm.entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hzerrad on 17-Mar-18.
 */

public class Question {
    private String text;
    private ArrayList<Answer> correctAnswer;

    public Question(String text, Answer... correctAnswer){
        this.correctAnswer = new ArrayList<>();
        this.text = text;
        this.correctAnswer.addAll(Arrays.asList(correctAnswer));
    }

    public Question(String text){
        this.correctAnswer = new ArrayList<>();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Answer> getAnswers() {
        return correctAnswer;
    }

    public void setAnswers(ArrayList<Answer> answer) {
        this.correctAnswer = answer;
    }

    public void setAnswers(Answer...answers){
        this.correctAnswer.addAll(Arrays.asList(answers));
    }


    @Override
    public String toString(){
        return "Question[" + text +"]";
    }
}
