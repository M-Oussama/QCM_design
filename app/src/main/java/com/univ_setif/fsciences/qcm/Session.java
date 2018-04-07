package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.control.AnswerCTRL;
import com.univ_setif.fsciences.qcm.control.SwipeAdapter;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.fragments.DisplayQcm;

import java.util.ArrayList;

public class Session extends FragmentActivity implements DisplayQcm.SwipeListener {

    private Answer answers[];
    private ArrayList<QCM> qcmList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        answers = new Answer[20];
        ViewPager viewPager = findViewById(R.id.viewPager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this);
        qcmList = swipeAdapter.getQcmList();
        viewPager.setAdapter(swipeAdapter);
        viewPager.setPageMargin(40);
        viewPager.setOffscreenPageLimit(20);

    }

    @Override
    public void onSwipeIn(int position) {
        TextView questionNumber = findViewById(R.id.showQuestionNumber);
        String display = "Question "+ position;
        questionNumber.setText(display);
    }

    @Override
    public void onAnswer(int position, Answer answer){
        answers[position-1] = answer;
    }

    private boolean hasEmpty(Answer[] answers){
        for (Answer ans:
             answers) {
            if(ans == null)
                return true;
        }

        return false;
    }

    public void onSubmitClickListener(View view) {

        AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);
        final int mynote = answerCTRL.checkAnswers(answers);

        AlertDialog.Builder confirm = new AlertDialog.Builder(Session.this);
        confirm.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent t = new Intent(Session.this, ShowCorrection.class);
                        t.putExtra("note", 20);
                        startActivity(t);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        if(hasEmpty(answers))
            confirm.setMessage("Vous avez pas répondu au tout les questions proposées. Puisque le système " +
                    "d'évaluation n'est pas négative, nous vous conseillons de répondre comme mème.\n" +
                    "Etes-vous sur d'envoyer vos réponses maintenant?");
        else
            confirm.setMessage("Voulez-vous vraiment envoyer vos réponses? Vous pouvez plus modifier vos réponses dès" +
                    " ce point.");


        AlertDialog exit = confirm.create();
        exit.setCanceledOnTouchOutside(true);
        exit.setTitle("Submit");
        exit.show();

    }
}
