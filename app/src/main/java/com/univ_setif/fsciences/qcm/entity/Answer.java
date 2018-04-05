package com.univ_setif.fsciences.qcm.entity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hzerrad on 17-Mar-18.
 */

public class Answer {

    private String text;

    public Answer(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return "Answer[" + text + "].";
    }

    @Override
    public boolean equals(Object obj) {
        return this.getText().equals(((Answer)obj).getText());
    }
}
