package com.univ_setif.fsciences.qcm;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.univ_setif.fsciences.qcm.R;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.adminico,
            R.drawable.homeico,
            R.drawable.userico};
   public static  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       context=this;
        final ViewPager viewpager = findViewById(R.id.viewpage);
        CardView cardView = findViewById(R.id.card);

        PagerAdapter pagerAdapter = new com.univ_setif.fsciences.qcm.Main.PagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(pagerAdapter);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewpager);
        TabLayout.Tab tabt;
        viewpager.setCurrentItem(1);
       setupTabIcons();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }
        });
    }

   private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
    }
}
