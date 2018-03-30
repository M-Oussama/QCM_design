package com.univ_setif.fsciences.qcm;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.control.SwipeAdapter;

public class Session extends FragmentActivity implements DisplayQcm.SwipeListener {

    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SwipeAdapter(getSupportFragmentManager(), Session.this));
        viewPager.setOffscreenPageLimit(20);


    }


    @Override
    public void onSwipeIn(int position) {
        TextView questionNumber = findViewById(R.id.showQuestionNumber);
        String display = "Question "+ position;
        questionNumber.setText(display);
    }

}
