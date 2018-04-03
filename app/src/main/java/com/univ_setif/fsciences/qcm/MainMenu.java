package com.univ_setif.fsciences.qcm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.univ_setif.fsciences.qcm.control.LoginCTRL;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.fragments.AdminSpaceFragment;
import com.univ_setif.fsciences.qcm.fragments.MainFragment;
import com.univ_setif.fsciences.qcm.fragments.UserSpaceFragment;

public class MainMenu extends AppCompatActivity {

    private mcqCTRL controleur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /*
        PageSelectAdapter adapter = new PageSelectAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.mainSlider);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        */

        controleur = new mcqCTRL(this);
        controleur.openReadable();
    }

    public void onStartClickListener(View v){
        Intent t = new Intent(MainMenu.this, Session.class);
        startActivity(t);
    }

    public void onExitClickListener(View V){
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
        exit.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                dialogInterface.cancel();
                return true;
            }
        });
        exit.setTitle("Exit");
        exit.show();
    }

    public void onAdminClickListener(View V){

        //TODO Create AlertDialog

        //Building Dialog Popup
        AlertDialog.Builder mLogin = new AlertDialog.Builder(MainMenu.this);

        final View mLoginView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        mLogin.setView(mLoginView);

        //Creating Dialog Popup
        final AlertDialog login = mLogin.create();
        login.setCancelable(false);
        login.setCanceledOnTouchOutside(true);



        //Retrieving Components
        final EditText password = (EditText) mLoginView.findViewById(R.id.password);

        final Button mSubmit    = (Button) mLoginView.findViewById(R.id.login);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pass = password.getText().toString();
                LoginCTRL.verify(getApplicationContext(), pass);
                login.cancel();
            }
        });

        mSubmit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            final String pass = password.getText().toString();
                            LoginCTRL.verify(getApplicationContext(), pass);
                            login.cancel();
                            return true;
                        default:
                            break;
                    }
                }

                return false;
            }
        });

        login.show();
    }

    public class PageSelectAdapter extends FragmentStatePagerAdapter{

        public PageSelectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new AdminSpaceFragment();

                case 1:
                    return new MainFragment();

                case 2:
                    return new UserSpaceFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
