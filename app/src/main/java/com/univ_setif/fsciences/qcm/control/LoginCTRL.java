package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.MCQManager;
import com.univ_setif.fsciences.qcm.Main.AdminSpace;
import com.univ_setif.fsciences.qcm.fragments.RecyclerViewFragment;

/**
 * Created by hzerrad on 18-Mar-18.
 */

public class LoginCTRL {
    private static final String PASSWORD = "admin";

    public static void verify(Context context, String password){
        if(password.equals(PASSWORD)) {
            Toast t = Toast.makeText(context.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT);
            t.show();
            getSession(context.getApplicationContext());

            AdminSpace.pd.cancel();
        }else{
            Toast t = Toast.makeText(context.getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
            t.show();
            AdminSpace.pd.cancel();
        }
    }

    private static void getSession(Context context){
        Intent t = new Intent(context, MCQManager.class);
        t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(t);

    }
}
