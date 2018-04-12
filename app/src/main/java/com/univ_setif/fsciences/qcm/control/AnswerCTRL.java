package com.univ_setif.fsciences.qcm.control;

import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

/**
 * Created by hzerrad on 03-Apr-18.
 */

public class AnswerCTRL {
    private final ArrayList<QCM> qcm;

    private double note=0;

    public AnswerCTRL(ArrayList<QCM> qcm){
        this.qcm = qcm;
    }


    public double checkAnswers(ArrayList[] answers) {

        for (int i=0; i<qcm.size(); i++) {
            if(!(answers[i].size() == 0)){
                if(answers[i].equals(qcm.get(i).getQuestion().getAnswers()))
                    note++;
                else if(qcm.get(i).getQuestion().getAnswers().containsAll(answers[i]))
                    note += 0.5;
            }
        }

        return note;
    }
}
