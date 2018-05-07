package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.AnswerCTRL;
import com.univ_setif.fsciences.qcm.control.SwipeAdapter;
import com.univ_setif.fsciences.qcm.control.UserLogCTRL;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.fragments.DisplayQcm;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Session extends FragmentActivity implements DisplayQcm.SwipeListener {

    private SwipeAdapter swipeAdapter;
    private ViewPager viewPager;

    private ArrayList[] answers;
    private ArrayList<QCM> qcmList;
    private double myNote;

    private SessionTimer timer;
    private TextView timerView;
    private TextView questionNumber;

    private int nbrQCM;
    private String date;
    private int evalSystem;
    private String dbname,dbfullname;
    private  String qcmtime;
    public long minute,second;
    public String elpsedtime;


    private boolean finalized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        SharedPreferences userchoice = getApplicationContext().getSharedPreferences("userchoice",MODE_PRIVATE);

        if(userchoice.getString("dbname","ouss").equals("GL")){
            dbname = "GL";
            dbfullname = new mcqCTRL(getApplicationContext(),null).getDatabaseData().get(dbname);

        }else{
            dbname=userchoice.getString("dbname","GL");
            dbfullname = new mcqCTRL(getApplicationContext(),null).getDatabaseData().get(dbname);
        }

        Typeface gunnyRewritten = Typeface.createFromAsset(Session.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");

        timerView      = findViewById(R.id.session_timer);
        timerView.setTypeface(gunnyRewritten);
        viewPager      = findViewById(R.id.viewPager);
        questionNumber = findViewById(R.id.showQuestionNumber);
        questionNumber.setTypeface(gunnyRewritten);
        Button evaluate = findViewById(R.id.submit);
        evaluate.setTypeface(gunnyRewritten);

        if (getIntent().getStringExtra("Log") != null){

            evaluate.setVisibility(View.GONE);

            qcmList = (ArrayList<QCM>) getIntent().getSerializableExtra("qcmList");
            answers = (ArrayList[]) getIntent().getSerializableExtra("answers");
            String elapsedTime = getIntent().getStringExtra("elapsed");
            nbrQCM = qcmList.size();

            swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this, qcmList, answers,dbname);
            viewPager.setAdapter(swipeAdapter);
            viewPager.setPageMargin(40);
            viewPager.setOffscreenPageLimit(nbrQCM);

            timerView.setText(elapsedTime);
            timerView.setTypeface(null, Typeface.BOLD);
            timerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

            finalized = true;
        }

        else {

            DateFormat df = DateFormat.getDateTimeInstance();
            date = df.format(Calendar.getInstance().getTime());

            long minutes, seconds;

            SharedPreferences sp = getSharedPreferences("adminSettings", MODE_PRIVATE);
            minutes    = sp.getLong("minutes", 10);
            seconds    = sp.getLong("secondes", 0);
            nbrQCM     = sp.getInt("nbrQCM", 20);
            evalSystem = sp.getInt("evalSystem", AnswerCTRL.PARTIEL);
            minute =minutes;
            second = seconds;

            answers = new ArrayList[nbrQCM];

            for (int i = 0; i < nbrQCM; i++)
                answers[i] = new ArrayList<Answer>();

            timerView = findViewById(R.id.session_timer);
            viewPager = findViewById(R.id.viewPager);
            swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this, nbrQCM,dbname);
            qcmList = swipeAdapter.getQcmList();
            viewPager.setAdapter(swipeAdapter);
            viewPager.setPageMargin(40);
            viewPager.setOffscreenPageLimit(nbrQCM);

            timer = new SessionTimer(minutes * 60 * 1000 + seconds * 1000, 1000);
            timer.start();
        }
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



    private void finalizeSession(){

        Button submit = findViewById(R.id.submit);
        submit.setVisibility(View.GONE);

        for (int i=0; i<nbrQCM; i++) {
            DisplayQcm fragment = (DisplayQcm) swipeAdapter.getFragment(i);

            ArrayList<Answer> correctAnswer = qcmList.get(i).getQuestion().getAnswers();

            fragment.setCorrectAnswers(correctAnswer);

            fragment.updateView();
        }
        timer.cancel();

        viewPager.setCurrentItem(0);
        finalized = true;
    }

    @Override
    public void onSwipeIn(int position) {
        String display = "Question "+ position;
        questionNumber.setText(display);
    }

    @Override
    public void onAnswer(int position, ArrayList<Answer> answer){
        answers[position-1] = answer;
    }

    @Override
    public void onBackPressed() {
        if(!finalized) {
            timer.cancel();

            final Dialog cancelsession = new Dialog(Session.this);

            LayoutInflater inflater = getLayoutInflater();
            View view=inflater.inflate(R.layout.alertdialog,null);

            Button positivebutton,negativebutton;
            TextView dialog_title,dialog_message;

            positivebutton =view.findViewById(R.id.positivebutton);
            negativebutton =view.findViewById(R.id.negative_button);
            dialog_title = view.findViewById(R.id.dialog_title);
            dialog_message = view.findViewById(R.id.dialog_message);

            Typeface gunnyRewritten = Typeface.createFromAsset(Session.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
            positivebutton.setTypeface(gunnyRewritten);
            negativebutton.setTypeface(gunnyRewritten);
            dialog_title.setTypeface(gunnyRewritten);
            dialog_message.setTypeface(gunnyRewritten);
            cancelsession.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    timer = timer.resume();
                }
            });

            dialog_title.setText("Retour au Menu Principale");
            dialog_message.setText("Voulez-vous vraiment retourner au menu principal? \n" +
                    "AVERTISSEMENT: Cette session sera perdue!");

            positivebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            negativebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cancelsession.cancel();
                    timer = timer.resume();
                }
            });

            cancelsession.setCanceledOnTouchOutside(false);
            cancelsession.setContentView(view);
            cancelsession.show();








        }
        else
            super.onBackPressed();
    }

    private boolean hasEmpty(ArrayList[] answers){
        for (ArrayList ans:
                answers) {
            if(ans.size() == 0)
                return true;
        }

        return false;
    }

    /*Submit Button*/
    public void onSubmitClickListener(View view) {
        timer.cancel();




        final Dialog evaluate = new Dialog(Session.this);

        LayoutInflater inflater = getLayoutInflater();
        View v=inflater.inflate(R.layout.alertdialog,null);

        Button positivebutton,negativebutton;
        TextView dialog_title,dialog_message;

        positivebutton =v.findViewById(R.id.positivebutton);
        negativebutton =v.findViewById(R.id.negative_button);
        dialog_title = v.findViewById(R.id.dialog_title);
        dialog_message = v.findViewById(R.id.dialog_message);
        evaluate.setCancelable(false);
        evaluate.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                timer = timer.resume();
            }
        });
      Typeface gunnyRewritten = Typeface.createFromAsset(Session.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
        positivebutton.setTypeface(gunnyRewritten);
        negativebutton.setTypeface(gunnyRewritten);
        dialog_title.setTypeface(gunnyRewritten);
        dialog_message.setTypeface(gunnyRewritten);

        dialog_title.setText("Submit");

        if(hasEmpty(answers))
            dialog_message.setText("Vous n'avez pas répondu à toutes les questions proposées! Voulez-vous vraimant soumettre " +
                    "vos réponses comme-mème?");
        else
            dialog_message.setText("Voulez-vous vraiment envoyer vos réponses? Vous pouvez plus modifier vos réponses dès" +
                    " ce point.");


        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elpsedtime = toTime(timer.getElapsed());
                dispatchForCorrection();

                TextView elpsedtime = findViewById(R.id.finaltime);
                elpsedtime.setVisibility(View.VISIBLE);
                elpsedtime.setText(toTime(timer.getElapsed()));
                elpsedtime.setTypeface(null, Typeface.BOLD);
                elpsedtime. setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                timer.cancel();
                timerView.setVisibility(View.GONE);
           /*     timerView.setText(toTime(timer.getElapsed()));
                timerView.setTypeface(null, Typeface.BOLD);
                timerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));*/


                try {
                    new UserLogCTRL(getApplicationContext())
                            .logSession(date, myNote, toTime(timer.getElapsed()), nbrQCM, qcmList, answers);
                } catch (IOException e) {
                    Log.w("Session", "An error occurred while logging the session");
                    e.printStackTrace();
                }

                finalizeSession();

                evaluate.cancel();

            }
        });
        negativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                evaluate.cancel();

            }
        });

        evaluate.setCanceledOnTouchOutside(false);
        evaluate.setContentView(v);
        evaluate.show();





/*
        AlertDialog.Builder confirm = new AlertDialog.Builder(Session.this);
        confirm.setCancelable(false)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        timer = timer.resume();
                    }
                })
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dispatchForCorrection();

                        timerView.setText(toTime(timer.getElapsed()));
                        timerView.setTypeface(null, Typeface.BOLD);
                        timerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

                        try {
                            new UserLogCTRL(getApplicationContext())
                                    .logSession(date, myNote, toTime(timer.getElapsed()), nbrQCM, qcmList, answers);
                        } catch (IOException e) {
                            Log.w("Session", "An error occurred while logging the session");
                            e.printStackTrace();
                        }

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
            confirm.setMessage("Vous n'avez pas répondu à toutes les questions proposées! Voulez-vous vraimant soumettre " +
                    "vos réponses comme-mème?");
        else
            confirm.setMessage("Voulez-vous vraiment envoyer vos réponses? Vous pouvez plus modifier vos réponses dès" +
                    " ce point.");


        AlertDialog exit = confirm.create();
        exit.setCanceledOnTouchOutside(true);
        exit.setTitle("Submit");
        exit.show();*/

    }

    private void dispatchForCorrection(){
        AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);
        myNote = answerCTRL.checkAnswers(answers, evalSystem);

        Intent t = new Intent(Session.this, ShowCorrection.class);
        t.putExtra("note", myNote);
        t.putExtra("Questioncount",qcmList.size());

        t.putExtra("Module",dbfullname);
        String qcmtime = minute+":"+second;

        t.putExtra("qcmtime",qcmtime);
        t.putExtra("usertime",elpsedtime);

        startActivity(t);
    }

    /**
     * A Helper class for managing the timer of a Session
     */
    private class SessionTimer extends CountDownTimer{

        private long initialTime;
        private long timeLeft;

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
            timerView.setText(toTime(l));
        }

        @Override
        public void onFinish() {
            timerView.setText("Terminé!");
            timerView.setTypeface(null, Typeface.BOLD);
            timerView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);

            @SuppressWarnings("unchecked")
            final double myNote = answerCTRL.checkAnswers(answers, evalSystem);

            Intent t = new Intent(Session.this, ShowCorrection.class);
            t.putExtra("note", myNote);
            t.putExtra("Questioncount",qcmList.size());

            t.putExtra("Module",dbfullname);
            String qcmtime = minute+":"+second;
            t.putExtra("qcmtime",qcmtime);
            t.putExtra("usertime",qcmtime);
            t.putExtra("correctAnswer",DisplayQcm.correctanswer);
            t.putExtra("skipped",DisplayQcm.questionignored);
            startActivity(t);
            finalizeSession();
        }
    }
}