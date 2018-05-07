package com.univ_setif.fsciences.qcm;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.Main.Home;
import com.univ_setif.fsciences.qcm.Main.UserSpace;
import com.univ_setif.fsciences.qcm.control.MainPagerAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    TabLayout tabLayout;
    ActivityOptions options;
    private int[] tabIcons = {
            R.drawable.adminico,
            R.drawable.homeico,
            R.drawable.userico};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final ViewPager viewpager = findViewById(R.id.viewpage);


        PagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setOffscreenPageLimit(1);

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
        SharedPreferences user = getSharedPreferences("userInfo", MODE_PRIVATE);
        String dbName = user.getString("module", "GL");

        ArrayList<QCM> tester = (ArrayList) new mcqCTRL(MainMenu.this, dbName).getAllQCM();

        if(tester == null || tester.size() < 10){
            Toast.makeText(MainMenu.this, "Please add at least 10 questions to the selected subject!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent t = new Intent(MainMenu.this, Session.class);
        startActivity(t);
    }

    public void onUserClick(View V) {
        Intent t = new Intent(MainMenu.this, UserSpace.class);
        startActivity(t);
    }

    public void onHelpClick(View V) {
        Intent t = new Intent(MainMenu.this, UserHelp.class);
        startActivity(t);
    }
    @Override
    public void onBackPressed() {



        final Dialog logoutconfirmation = new Dialog(MainMenu.this);

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.alertdialog,null);

        Button positivebutton,negativebutton;
        TextView dialog_title,dialog_message;

        positivebutton =view.findViewById(R.id.positivebutton);
        negativebutton =view.findViewById(R.id.negative_button);
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_message = view.findViewById(R.id.dialog_message);

        dialog_title.setText("EXIT");
        dialog_message.setText("Voulez-vous vraiment quitter l'application?");

        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        negativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutconfirmation.cancel();

            }
        });
        logoutconfirmation.setCanceledOnTouchOutside(false);
        logoutconfirmation.setContentView(view);
        logoutconfirmation.show();


    }

    public void onSettingsclick(View view) {
        Intent setting = new Intent(MainMenu.this,Settings.class);
        final Pair  pairs ;
        pairs=new Pair<View ,String>(Home.Settingsicon,"Settingsicon");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(MainMenu.this,Home.Settingsicon,"Settingsicon");
            startActivity(setting,options.toBundle());
        }else{
            startActivity(setting);
        }

    }
}
