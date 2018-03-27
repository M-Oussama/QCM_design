package com.univ_setif.fsciences.qcm.entity;

/**
 * Created by hzerrad on 24-Mar-18.
 */

public class QCM {
    private Question question;
    private Answer ans1;
    private Answer ans2;
    private Answer ans3;
    private Answer ans4;

    public QCM(Question question, Answer ans1, Answer ans2, Answer ans3, Answer ans4) {
        this.question = question;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAns1() {
        return ans1;
    }

    public void setAns1(Answer ans1) {
        this.ans1 = ans1;
    }

    public Answer getAns2() {
        return ans2;
    }

    public void setAns2(Answer ans2) {
        this.ans2 = ans2;
    }

    public Answer getAns3() {
        return ans3;
    }

    public void setAns3(Answer ans3) {
        this.ans3 = ans3;
    }

    public Answer getAns4() {
        return ans4;
    }

    public void setAns4(Answer ans4) {
        this.ans4 = ans4;
    }
}
