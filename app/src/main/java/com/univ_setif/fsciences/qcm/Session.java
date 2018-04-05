package com.univ_setif.fsciences.qcm;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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
    private TextView noteDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        answers = new Answer[20];
        noteDisplay = findViewById(R.id.note);
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

    public void onSubmitClickListener(View view) {
        AnswerCTRL answerCTRL = new AnswerCTRL(qcmList);
        int mynote =answerCTRL.checkAnswers(answers);
        noteDisplay.setText("noteDisplay: "+mynote);
    }
}
