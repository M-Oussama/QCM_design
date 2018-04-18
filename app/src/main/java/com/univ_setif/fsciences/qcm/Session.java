package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
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
    private TextView sessionTimer;
    private SessionTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        answers = new ArrayList[20];
        for (int i=0; i<20; i++)
            answers[i] = new ArrayList<Answer>();

        sessionTimer = findViewById(R.id.session_timer);
        viewPager = findViewById(R.id.viewPager);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this);
        qcmList = swipeAdapter.getQcmList();
        viewPager.setAdapter(swipeAdapter);
        viewPager.setPageMargin(40);
        viewPager.setOffscreenPageLimit(20);

        timer = new SessionTimer(15*1000, 1000);
        timer.start();
    }

    public String toTime(long time){
        long minutes = time / (60*1000);
        long seconds = (time % 60000) / 1000;

        String timeLeft;

        if(minutes < 10) timeLeft = "0";

        timeLeft = minutes + ":";

        if(seconds < 10) timeLeft += "0";

        timeLeft += seconds;

        return timeLeft;
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

        timer.cancel();

        AlertDialog.Builder confirm = new AlertDialog.Builder(Session.this);
        confirm.setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dispatchForCorrection();
                       finalizeSession();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timer = timer.resume();
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

    public void dispatchForCorrection(){
            AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);

            @SuppressWarnings("unchecked")
            final double myNote = answerCTRL.checkAnswers(answers);

            Intent t = new Intent(Session.this, ShowCorrection.class);
            t.putExtra("note", myNote);
            startActivity(t);
    }

    private class SessionTimer extends CountDownTimer{

        private long initialTime;
        private long timeLeft;

        private long getInitialTime() {
            return initialTime;
        }

        private void setInitialTime(long l){
            initialTime = l;
        }

        SessionTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            initialTime = millisInFuture;
        }

        SessionTimer resume(){
            SessionTimer newTimer = new SessionTimer(timeLeft, 1000);
            newTimer.setInitialTime(initialTime);
            newTimer.start();
            return newTimer;
        }

        long getElapsed() {
            return initialTime - timeLeft;
        }

        @Override
        public void onTick(long l) {
            timeLeft = l;
            sessionTimer.setText(toTime(l));
        }

        @Override
        public void onFinish() {
            sessionTimer.setText("Terminé!");
            sessionTimer.setTypeface(null, Typeface.BOLD);
            sessionTimer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);

            @SuppressWarnings("unchecked")
            final double myNote = answerCTRL.checkAnswers(answers);

            Intent t = new Intent(Session.this, ShowCorrection.class);
            t.putExtra("note", myNote);
            startActivity(t);
            finalizeSession();
        }
    }
}
