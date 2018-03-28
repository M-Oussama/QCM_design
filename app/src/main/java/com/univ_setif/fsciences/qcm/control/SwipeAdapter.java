package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.univ_setif.fsciences.qcm.DisplayQcm;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

/**
 * Created by hzerrad on 28-Mar-18.
 */

public class SwipeAdapter extends FragmentPagerAdapter {

    private ArrayList<QCM> qcmList;
    private Context mContext;

    public SwipeAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        mcqCTRL ctrl = new mcqCTRL(mContext);
        qcmList = (ArrayList<QCM>) ctrl.getAllQCM();
        ctrl.close();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = new DisplayQcm();
        Bundle bundle = new Bundle();

        bundle.putInt("number", position);
        bundle.putString("questionText", qcmList.get(position).getQuestion().getText());
        bundle.putString("answer1", qcmList.get(position).getAns1().getText());
        bundle.putString("answer2", qcmList.get(position).getAns2().getText());
        bundle.putString("answer3", qcmList.get(position).getAns3().getText());
        bundle.putString("answer4", qcmList.get(position).getAns3().getText());
        bundle.putString("correctAnswer", qcmList.get(position).getQuestion().getAnswer().getText());

        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return qcmList.size();
    }

}
