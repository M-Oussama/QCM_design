package com.univ_setif.fsciences.qcm.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.entity.Answer;


public class DisplayQcm extends Fragment {

    private SwipeListener swipe;
    private int questionNumber;

    TextView qstText;
    Button [] ans;

    Answer answer;
    Answer correctAnswer;
    int answerPosition;

    public DisplayQcm() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        questionNumber = getArguments().getInt("number");

        View view = inflater.inflate(R.layout.fragment_display_qcm, container, false);

        qstText   = view.findViewById(R.id.questionContent);
        ans = new Button[4];
        ans[0]      = view.findViewById(R.id.answerContent1);
        ans[1]      = view.findViewById(R.id.answerContent2);
        ans[2]      = view.findViewById(R.id.answerContent3);
        ans[3]      = view.findViewById(R.id.answerContent4);


        ans[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[1].setBackgroundResource(android.R.drawable.btn_default);
                ans[2].setBackgroundResource(android.R.drawable.btn_default);
                ans[3].setBackgroundResource(android.R.drawable.btn_default);
                ans[0].setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans[0].getText().toString());
                answerPosition = 0;
                swipe.onAnswer(questionNumber, answer);
            }
        });

        ans[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[0].setBackgroundResource(android.R.drawable.btn_default);
                ans[2].setBackgroundResource(android.R.drawable.btn_default);
                ans[3].setBackgroundResource(android.R.drawable.btn_default);
                ans[1].setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans[1].getText().toString());
                answerPosition = 1;
                swipe.onAnswer(questionNumber, answer);
            }
        });

        ans[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[1].setBackgroundResource(android.R.drawable.btn_default);
                ans[0].setBackgroundResource(android.R.drawable.btn_default);
                ans[3].setBackgroundResource(android.R.drawable.btn_default);
                ans[2].setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans[2].getText().toString());
                answerPosition = 2;
                swipe.onAnswer(questionNumber, answer);
            }
        });

        ans[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans[1].setBackgroundResource(android.R.drawable.btn_default);
                ans[2].setBackgroundResource(android.R.drawable.btn_default);
                ans[0].setBackgroundResource(android.R.drawable.btn_default);
                ans[3].setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans[3].getText().toString());
                answerPosition = 3;
                swipe.onAnswer(questionNumber, answer);
            }
        });

        qstText.setText(getArguments().getString("questionText"));
        ans[0].setText(getArguments().getString("answer1"));
        ans[1].setText(getArguments().getString("answer2"));
        ans[2].setText(getArguments().getString("answer3"));
        ans[3].setText(getArguments().getString("answer4"));


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && getView() != null)
                swipe.onSwipeIn(questionNumber);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        swipe = (SwipeListener) activity;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void updateView() {

        ans[0].setClickable(false);
        ans[1].setClickable(false);
        ans[2].setClickable(false);
        ans[3].setClickable(false);

        if(correctAnswer.equals(answer)){
            ans[0].setBackgroundResource(android.R.drawable.btn_default);
            ans[1].setBackgroundResource(android.R.drawable.btn_default);
            ans[2].setBackgroundResource(android.R.drawable.btn_default);
            ans[3].setBackgroundResource(android.R.drawable.btn_default);

            ans[answerPosition].setBackgroundResource(R.color.correctAnswer);

        }else{
            ans[0].setBackgroundResource(android.R.drawable.btn_default);
            ans[1].setBackgroundResource(android.R.drawable.btn_default);
            ans[2].setBackgroundResource(android.R.drawable.btn_default);
            ans[3].setBackgroundResource(android.R.drawable.btn_default);

            ans[answerPosition].setBackgroundResource(R.color.wrongAnswer);

            if(ans[0].getText().toString().equals(correctAnswer.getText()))
                ans[0].setBackgroundResource(R.color.correctAnswer);
            else if(ans[1].getText().toString().equals(correctAnswer.getText()))
                ans[1].setBackgroundResource(R.color.correctAnswer);
            else if(ans[2].getText().toString().equals(correctAnswer.getText()))
                ans[2].setBackgroundResource(R.color.correctAnswer);
            else
                ans[3].setBackgroundResource(R.color.correctAnswer);
        }
    }

    public interface SwipeListener {
        void onSwipeIn(int position);
        void onAnswer(int position, Answer answer);
    }

}
