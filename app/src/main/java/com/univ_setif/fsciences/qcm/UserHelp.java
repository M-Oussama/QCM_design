package com.univ_setif.fsciences.qcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
    }

    public void onAdminHelpClick(View v){
        Intent i = new Intent(UserHelp.this, AdminHelp.class);
        startActivity(i);
    }

    public void onReturnClick(View v){
        finish();
    }
}
