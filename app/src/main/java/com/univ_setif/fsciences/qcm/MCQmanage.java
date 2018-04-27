package com.univ_setif.fsciences.qcm;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.fragments.RecyclerViewFragment;

import java.util.ArrayList;

public class MCQmanage extends AppCompatActivity {
    int pageCount = 1;
    RecyclerView recyclerView;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqmanage);
        HollyViewPager hollyViewPager = findViewById(R.id.hollyViewPager);
        context = this;
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {

                return (float) 0.5;
            }
        });
        hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return pageCount;
            }
            @Override
            public CharSequence getPageTitle(int position) {


                return "Génie Logiciel";
            }

            @Override
            public Fragment getItem(int position) {


                return new RecyclerViewFragment(position);


            }

        });
    }
    @Override
    public void onBackPressed() { }

}
