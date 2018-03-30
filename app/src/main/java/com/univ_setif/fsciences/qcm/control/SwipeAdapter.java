package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.univ_setif.fsciences.qcm.DisplayQcm;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by hzerrad on 28-Mar-18.
 */

public class SwipeAdapter extends FragmentPagerAdapter {

    private ArrayList<QCM> qcmList;

    public SwipeAdapter(FragmentManager fm, Context context) {
        super(fm);
        mcqCTRL ctrl = new mcqCTRL(context);
        qcmList = (ArrayList<QCM>) ctrl.getAllQCM();
        qcmList = (ArrayList<QCM>) getRandomItems(qcmList);
        ctrl.close();
    }

    private int[] distinctRandom(int bound){
        final Random random = new Random();
        final Set<Integer> intSet = new HashSet<>();
        while (intSet.size() < 20) {
            intSet.add(random.nextInt(bound));
        }
        final int[] ints = new int[intSet.size()];
        final Iterator<Integer> iter = intSet.iterator();
        for (int i = 0; iter.hasNext(); ++i) {
            ints[i] = iter.next();
        }

        return ints;
    }


    private List<QCM> getRandomItems(List<QCM> listOfObjects) {
        final int[] rand = distinctRandom(listOfObjects.size()-1);

        List<QCM> qcm = new ArrayList<>();

        for (int index: rand)
            qcm.add(listOfObjects.get(index));

        return qcm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = new DisplayQcm();

        Bundle bundle = new Bundle();

        bundle.putInt("number", position+1);
        bundle.putString("questionText", qcmList.get(position).getQuestion().getText());
        bundle.putString("answer1", qcmList.get(position).getAns1().getText());
        bundle.putString("answer2", qcmList.get(position).getAns2().getText());
        bundle.putString("answer3", qcmList.get(position).getAns3().getText());
        bundle.putString("answer4", qcmList.get(position).getAns4().getText());
        bundle.putString("correctAnswer", qcmList.get(position).getQuestion().getAnswer().getText());

        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return qcmList.size();
    }

}
