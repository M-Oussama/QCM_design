package com.univ_setif.fsciences.qcm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.univ_setif.fsciences.qcm.Main.UserSpace;
import com.univ_setif.fsciences.qcm.control.LoginCTRL;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class MainMenu extends AppCompatActivity {
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.adminico,
            R.drawable.homeico,
            R.drawable.userico};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewpager = findViewById(R.id.viewpage);

        PagerAdapter pagerAdapter = new com.univ_setif.fsciences.qcm.control.PagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewpager);

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

    public void onStartClick(View v) {
        Intent t = new Intent(MainMenu.this, Session.class);
        startActivity(t);
    }

    public void onUserClick(View V) {
        Intent t = new Intent(MainMenu.this, UserSpace.class);
        startActivity(t);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirm = new AlertDialog.Builder(MainMenu.this);
        confirm.setMessage("Voulez-vous vraiment quitter l'application?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog exit = confirm.create();
        exit.setCanceledOnTouchOutside(true);
        exit.setTitle("Exit");
        exit.show();
    }
}
