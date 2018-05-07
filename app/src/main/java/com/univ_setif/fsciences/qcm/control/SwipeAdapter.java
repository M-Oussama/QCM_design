package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.fragments.DisplayQcm;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by hzerrad on 28-Mar-18.
 */

public class SwipeAdapter extends FragmentPagerAdapter {

    private ArrayList<QCM> qcmList;
    private ArrayList<Answer>[] answers;
    private HashMap<Integer, String> pageReferenceMap;
    private FragmentManager fm;
    private boolean isLog = false;

    public ArrayList<QCM> getQcmList() {
        return qcmList;
    }

    public SwipeAdapter(FragmentManager fm, Context context, ArrayList<QCM> list, int size) {
        super(fm);
        this.fm = fm;
        pageReferenceMap = new HashMap<>();

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String dbName = sp.getString("module", "GL");

        mcqCTRL ctrl = new mcqCTRL(context, dbName);
        ArrayList<QCM> allQCM = (ArrayList<QCM>) ctrl.getAllQCM();
        ctrl.close();

        Collections.shuffle(allQCM);
        qcmList = new ArrayList<>();
        for(int i=0; i<size; i++)
            qcmList.add(allQCM.get(i));
    }

    public SwipeAdapter(FragmentManager fm, Context context, ArrayList<QCM> list, ArrayList<Answer>[] answers) {
        super(fm);
        this.fm = fm;
        pageReferenceMap = new HashMap<>();

        qcmList = list;
        this.answers = answers;
        isLog = true;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag = new DisplayQcm();
        frag.onCreate(null);

        Bundle bundle = new Bundle();

        bundle.putInt("number", position+1);
        bundle.putString("questionText", qcmList.get(position).getQuestion().getText());
        bundle.putString("answer1", qcmList.get(position).getAns1().getText());
        bundle.putString("answer2", qcmList.get(position).getAns2().getText());
        bundle.putString("answer3", qcmList.get(position).getAns3().getText());
        bundle.putString("answer4", qcmList.get(position).getAns4().getText());

        pageReferenceMap.put(position, frag.getTag());

        frag.setArguments(bundle);

        if(isLog) {
            DisplayQcm fragment = (DisplayQcm) frag;
            fragment.fromLog();
            fragment.setUserAnswer(answers[position]);
            fragment.setCorrectAnswers(qcmList.get(position).getQuestion().getAnswers());
        }
        return frag;
    }

    @Override
    public int getItemPosition(Object object) {
        DisplayQcm fragment = (DisplayQcm) object;

        if(fragment != null){
            fragment.updateView();
        }
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        if (object instanceof Fragment) {
            DisplayQcm fragment = (DisplayQcm) object;
            String tag = fragment.getTag();
            pageReferenceMap.put(position, tag);

            if(isLog) {
                fragment.fromLog();
                fragment.setUserAnswer(answers[position]);
                fragment.setCorrectAnswers(qcmList.get(position).getQuestion().getAnswers());
            }
        }

        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;

        String tag = pageReferenceMap.get(position);

        if (tag != null)
            fragment = this.fm.findFragmentByTag(tag);
        return fragment;
    }


    @Override
    public int getCount() {
        return qcmList.size();
    }

}
