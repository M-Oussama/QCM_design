package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.control.AnswerCTRL;
import com.univ_setif.fsciences.qcm.control.SwipeAdapter;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.fragments.DisplayQcm;

import java.util.ArrayList;

public class Session extends FragmentActivity implements DisplayQcm.SwipeListener {

    private ArrayList[] answers;
    private ArrayList<QCM> qcmList;
    private SwipeAdapter swipeAdapter;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        answers = new ArrayList[20];
        for (int i=0; i<20; i++)
            answers[i] = new ArrayList<Answer>();

        viewPager = findViewById(R.id.viewPager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this);
        qcmList = swipeAdapter.getQcmList();
        viewPager.setAdapter(swipeAdapter);
        viewPager.setPageMargin(40);
        viewPager.setOffscreenPageLimit(20);

    }


    public void finalizeSession(){

        Button submit = findViewById(R.id.submit);
        submit.setVisibility(View.GONE);

        for (int i=0; i<20; i++) {
            DisplayQcm fragment = (DisplayQcm) swipeAdapter.getFragment(i);

            ArrayList<Answer> correctAnswer = qcmList.get(i).getQuestion().getAnswers();

            fragment.setCorrectAnswers(correctAnswer);

            fragment.updateView();
        }

        viewPager.setCurrentItem(0);
    }

    @Override
    public void onSwipeIn(int position) {
        TextView questionNumber = findViewById(R.id.showQuestionNumber);
        String display = "Question "+ position;
        questionNumber.setText(display);
    }

    @Override
    public void onAnswer(int position, ArrayList<Answer> answer){
                answers[position-1] = answer;
    }



    private boolean hasEmpty(ArrayList[] answers){
        for (ArrayList ans:
             answers) {
            if(ans.size() == 0)
                return true;
        }

        return false;
    }

    public void onSubmitClickListener(View view) {

        AlertDialog.Builder confirm = new AlertDialog.Builder(Session.this);
        confirm.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);

                        @SuppressWarnings("unchecked")
                        final double myNote = answerCTRL.checkAnswers(answers);

                        Intent t = new Intent(Session.this, ShowCorrection.class);
                        t.putExtra("note", myNote);
                        startActivity(t);
                        finalizeSession();
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
