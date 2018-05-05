package com.univ_setif.fsciences.qcm.control;

import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by hzerrad on 03-Apr-18.
 */

public class AnswerCTRL {
    private final ArrayList<QCM> qcm;
    public static final int TOUTE_OU_RIEN = 0;
    public static final int PARTIEL = 1;
    public static final int NEGATIVE = 2;


    private double note=0;

    public AnswerCTRL(ArrayList<QCM> qcm){
        this.qcm = qcm;
    }


    public double checkAnswers(ArrayList[] answers, int corrigeType) {
        switch(corrigeType) {
            case PARTIEL:
                for (int i = 0; i < qcm.size(); i++) {
                    if (!(answers[i].size() == 0)) {
                        if (answers[i].equals(qcm.get(i).getQuestion().getAnswers()))
                            note++;
                        else if (qcm.get(i).getQuestion().getAnswers().containsAll(answers[i]))
                            note += 0.5;
                    }
                }
                break;

            case TOUTE_OU_RIEN:
                for (int i = 0; i < qcm.size(); i++) {
                    if (!(answers[i].size() == 0)) {
                        if (answers[i].equals(qcm.get(i).getQuestion().getAnswers()))
                            note++;
                    }
                }
                break;

            case NEGATIVE:
                for (int i = 0; i < qcm.size(); i++) {
                    if (!(answers[i].size() == 0)) {
                        if (answers[i].equals(qcm.get(i).getQuestion().getAnswers()))
                            note++;
                    }else
                        note -= 0.25;
                }

                if(note < 0) note = 0;

                break;
        }


            note = round((note * 20) / qcm.size(), 2);

            return note;

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
