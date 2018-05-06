package com.univ_setif.fsciences.qcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_help);
    }

    public void onUserHelpClick(View v){
        finish();
    }

    public void onReturnHomeClick(View v){
        Intent i = new Intent(AdminHelp.this, MainMenu.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }
}
