package com.univ_setif.fsciences.qcm.control;

import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

/**
 * Created by hzerrad on 03-Apr-18.
 */

public class AnswerCTRL {
    private final ArrayList<QCM> qcm;

    public int getNote() {
        return note;
    }

    private int note=0;

    public AnswerCTRL(ArrayList<QCM> qcm){
        this.qcm = qcm;
    }


    public int checkAnswers(Answer[] answers) {
        for (int i=0; i<qcm.size(); i++) {
            if(qcm.get(i).getQuestion().getAnswer().getText().equals(answers[i].getText()))
                note++;

        }

        return note;
    }
}
