package com.univ_setif.fsciences.qcm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.univ_setif.fsciences.qcm.control.LoginCTRL;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class MainMenu extends AppCompatActivity {

    private mcqCTRL controleur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

       controleur = new mcqCTRL(this);
       controleur.openReadable();
       controleur.close();
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
        login.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                dialogInterface.cancel();
                return true;
            }
        });

        //Retrieving Components
        final EditText password = mLoginView.findViewById(R.id.password);
        final Button mSubmit    = mLoginView.findViewById(R.id.login);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pass = password.getText().toString();
                LoginCTRL.verify(getApplicationContext(), pass);
                login.cancel();
            }
        });

        login.show();
    }
}
