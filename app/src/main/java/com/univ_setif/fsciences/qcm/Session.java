package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
    private String dbfullname;
    private  String qcmtime;
    public long minutes,seconds;
    private TextView module;
    public String elpsedtime;
    String dbName;


    private boolean finalized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Typeface gunnyRewritten = Typeface.createFromAsset(Session.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");

        timerView      = findViewById(R.id.session_timer);
        timerView.setTypeface(gunnyRewritten, Typeface.BOLD);
        viewPager      = findViewById(R.id.viewPager);
        questionNumber = findViewById(R.id.showQuestionNumber);
        questionNumber.setTypeface(gunnyRewritten);
        module = findViewById(R.id.Module);
        module.setTypeface(gunnyRewritten);

        Button evaluate = findViewById(R.id.submit);
        evaluate.setTypeface(gunnyRewritten);

        if (getIntent().getStringExtra("Log") != null){

            evaluate.setVisibility(View.GONE);

            qcmList = (ArrayList<QCM>) getIntent().getSerializableExtra("qcmList");
            answers = (ArrayList[]) getIntent().getSerializableExtra("answers");
            String elapsedTime = getIntent().getStringExtra("elapsed");
            nbrQCM = qcmList.size();

            swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this, qcmList, answers);
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

            SharedPreferences sp = getSharedPreferences("adminSettings", MODE_PRIVATE);
            minutes    = sp.getLong("minutes", 10);
            seconds    = sp.getLong("secondes", 0);
            nbrQCM     = sp.getInt("nbrQCM", 20);
            evalSystem = sp.getInt("evalSystem", AnswerCTRL.PARTIEL);


            answers = new ArrayList[nbrQCM];

            for (int i = 0; i < nbrQCM; i++)
                answers[i] = new ArrayList<Answer>();

            timerView = findViewById(R.id.session_timer);
            viewPager = findViewById(R.id.viewPager);
            SharedPreferences user = Session.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            dbName = user.getString("module", "GL");
            dbfullname= new mcqCTRL(getApplicationContext(),null).getDatabaseData().get(dbName);
            module.setText(dbfullname);
            qcmList = (ArrayList) new mcqCTRL(Session.this, dbName).getAllQCM();
            swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), Session.this, qcmList, nbrQCM);
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

            dialog_title.setText("Retour au Menu Principale");
            dialog_message.setText("Voulez-vous vraiment retourner au menu principal? \n" +
                    "AVERTISSEMENT: Cette session sera perdue!");

            positivebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelsession.cancel();
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
      final Typeface gunnyRewritten = Typeface.createFromAsset(Session.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
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


                dispatchForCorrection();

                timerView.setText(toTime(timer.getElapsed()));
                timerView.setTypeface(gunnyRewritten, Typeface.BOLD);
                timerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

                try {
                    new UserLogCTRL(getApplicationContext(), true)
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
                timer = timer.resume();
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
        t.putExtra("dbname",dbName);
        t.putExtra("note", myNote);
        t.putExtra("Questioncount",qcmList.size());

        t.putExtra("Module",dbfullname);
        String qcmtime = toTime(minutes*60*1000 + seconds*1000);

        t.putExtra("qcmtime",qcmtime);
        t.putExtra("usertime",toTime(timer.getElapsed()));

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
            String qcmtime = toTime(minutes*60*1000 + seconds*1000);
            t.putExtra("qcmtime", qcmtime);
            t.putExtra("usertime",toTime(timer.getElapsed()));
            t.putExtra("correctAnswer",DisplayQcm.correctanswer);
            t.putExtra("dbname",dbName);
            t.putExtra("skipped",DisplayQcm.questionignored);
            startActivity(t);
            finalizeSession();
        }
    }
}