package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.univ_setif.fsciences.qcm.Main.AdminSpace;
import com.univ_setif.fsciences.qcm.Main.Home;
import com.univ_setif.fsciences.qcm.Main.UserSpace;

/**
 * Created by oussama on 06/04/2018.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
   private String frag[]={"AdminSpace","Home","UserSpace"};
   private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                AdminSpace admin = new AdminSpace();
                return admin;

            case 1:
                Home main = new Home();
                return main;
            case 2:
                UserSpace user = new UserSpace();
                return user;

                default: return null;
        }

    }


    @Override
    public int getCount() {
        return frag.length;
    }




}
