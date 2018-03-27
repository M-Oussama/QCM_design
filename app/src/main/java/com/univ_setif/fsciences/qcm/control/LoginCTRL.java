package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.MCQManager;
import com.univ_setif.fsciences.qcm.MainMenu;
import com.univ_setif.fsciences.qcm.R;

/**
 * Created by hzerrad on 18-Mar-18.
 */

public class LoginCTRL {
    private static final String PASSWORD = "admin";
    private static Context context;


    public static void verify(Context context, String password){
        if(password.equals(PASSWORD)) {
            Toast t = Toast.makeText(context.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
            t.show();
            getSession(context.getApplicationContext());
        }else{
            Toast t = Toast.makeText(context.getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    protected static void getSession(Context context){
        Intent t = new Intent(context, MCQManager.class);
        context.startActivity(t);
    }
}
