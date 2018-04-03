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
    private int position;

    TextView qstText;
    Button ans1;
    Button ans2;
    Button ans3;
    Button ans4;

    Answer answer;

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

        position = getArguments().getInt("number");

        View view = inflater.inflate(R.layout.fragment_display_qcm, container, false);

        qstText   = view.findViewById(R.id.questionContent);
        ans1      = view.findViewById(R.id.answerContent1);
        ans2      = view.findViewById(R.id.answerContent2);
        ans3      = view.findViewById(R.id.answerContent3);
        ans4      = view.findViewById(R.id.answerContent4);


        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans1.getText().toString());
                swipe.onAnswer(position, answer);
            }
        });

        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans2.setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans2.getText().toString());
                swipe.onAnswer(position, answer);
            }
        });

        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans3.getText().toString());
                swipe.onAnswer(position, answer);
            }
        });

        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(R.color.selectedButton);
                answer = new Answer(ans4.getText().toString());
                swipe.onAnswer(position, answer);
            }
        });

        qstText.setText(getArguments().getString("questionText"));
        ans1.setText(getArguments().getString("answer1"));
        ans2.setText(getArguments().getString("answer2"));
        ans3.setText(getArguments().getString("answer3"));
        ans4.setText(getArguments().getString("answer4"));


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && getView() != null)
                swipe.onSwipeIn(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        swipe = (SwipeListener) activity;
    }

    public interface SwipeListener {
        void onSwipeIn(int position);
        void onAnswer(int position, Answer answer);
    }

}
