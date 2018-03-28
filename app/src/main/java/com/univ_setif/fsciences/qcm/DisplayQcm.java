package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class DisplayQcm extends Fragment {

    private OnSwipeInListener swipe;
    private int position;

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

        position = getArguments().getInt("position");

        View view = inflater.inflate(R.layout.fragment_display_qcm, container, false);

        final TextView qstText = view.findViewById(R.id.questionContent);
        final Button ans1      = view.findViewById(R.id.answerContent1);
        final Button ans2      = view.findViewById(R.id.answerContent2);
        final Button ans3      = view.findViewById(R.id.answerContent3);
        final Button ans4      = view.findViewById(R.id.answerContent4);


        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(R.color.selectedButton);
            }
        });

        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans2.setBackgroundResource(R.color.selectedButton);
            }
        });

        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(R.color.selectedButton);
            }
        });

        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundResource(android.R.drawable.btn_default);
                ans3.setBackgroundResource(android.R.drawable.btn_default);
                ans1.setBackgroundResource(android.R.drawable.btn_default);
                ans4.setBackgroundResource(R.color.selectedButton);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){

            swipe.onSwipeIn(position);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        Activity activity = (Activity) context;

        swipe = (OnSwipeInListener) activity;
    }

    public interface OnSwipeInListener{
        public void onSwipeIn(int position);
    }
}
