package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashMap;

public class ShowCorrection extends AppCompatActivity {
    Button returnMain;
    static Button addQuestion;
    double note = 0;

    LinearLayout linearLayout;
    Button correction;
    TextView Note,name,module,usertime,Qcmtime,Questioncount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_correction);

        //Note Display
        linearLayout = findViewById(R.id.linearLayout4);
        addQuestion =  findViewById(R.id.add_question);
        returnMain = findViewById(R.id.back_menu);
        correction = findViewById(R.id.correction);

        Note = findViewById(R.id.Note);
        name = findViewById(R.id.name);
        module = findViewById(R.id.module);
        usertime = findViewById(R.id.usertime);
        Qcmtime = findViewById(R.id.Qcmtime);

        Questioncount = findViewById(R.id.questioncount);
        note        = getIntent().getExtras().getDouble("note");
        String noteString = note + "/20";



        SharedPreferences username =getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        HashMap<String, String> result = (HashMap<String, String>) username.getAll();

        Note.setText("Note: "+ noteString);
        name.setText("Nom: " +result.get("firstname")+" "+result.get("surname"));
        module.setText(getIntent().getStringExtra("Module"));
        usertime.setText     ("Votre Temps       :     "+getIntent().getStringExtra("usertime"));
        Qcmtime.setText      ("Temps de L'examan :     " +getIntent().getStringExtra("qcmtime"));
        Questioncount.setText("Questions         :     "+Integer.valueOf(getIntent().getIntExtra("Questioncount",0)));

        if(note>=getIntent().getIntExtra("Questioncount",0)/2&& note <20){

            final Dialog felicitation = new Dialog(ShowCorrection.this);

            LayoutInflater inflater = getLayoutInflater();
            View view=inflater.inflate(R.layout.alertdialog,null);

            Button positivebutton,negativebutton;
            TextView dialog_title,dialog_message;
            ImageView imageView;
            positivebutton =view.findViewById(R.id.positivebutton);
            negativebutton =view.findViewById(R.id.negative_button);
            dialog_title = view.findViewById(R.id.dialog_title);
            dialog_message = view.findViewById(R.id.dialog_message);
            imageView = view.findViewById(R.id.image1);
            imageView.setImageResource(R.drawable.success);
            positivebutton.setVisibility(View.GONE);
            negativebutton.setVisibility(View.GONE);

            dialog_title.setText("Félicitation");
            dialog_message.setText("Bravo! Vous avez eu un bon note !" );


            felicitation.setCanceledOnTouchOutside(true);
            felicitation.setContentView(view);
            felicitation.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    felicitation.dismiss();
                }
            }, 3000);

        }else{

            final Dialog bad = new Dialog(ShowCorrection.this);

            LayoutInflater inflater = getLayoutInflater();
            View view=inflater.inflate(R.layout.alertdialog,null);

            Button positivebutton,negativebutton;
            TextView dialog_title,dialog_message;
            ImageView imageView;

            positivebutton =view.findViewById(R.id.positivebutton);
            negativebutton =view.findViewById(R.id.negative_button);
            dialog_title = view.findViewById(R.id.dialog_title);
            dialog_message = view.findViewById(R.id.dialog_message);
            imageView = view.findViewById(R.id.image1);
            imageView.setImageResource(R.drawable.fail_icon);
            positivebutton.setVisibility(View.GONE);
            negativebutton.setVisibility(View.GONE);

            dialog_title.setText("Félicitation");
            dialog_message.setText("OH NO ! Vous avez eu une mauvaise note  !" );


            bad.setCanceledOnTouchOutside(true);
            bad.setContentView(view);
            bad.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    bad.dismiss();
                }
            }, 3000);

        }

        //Add Question Button Display
        if(note==20) {


            final Dialog felicitation = new Dialog(ShowCorrection.this);

            LayoutInflater inflater = getLayoutInflater();
            View view=inflater.inflate(R.layout.alertdialog,null);

            Button positivebutton,negativebutton;
            TextView dialog_title,dialog_message;

            positivebutton =view.findViewById(R.id.positivebutton);
            negativebutton =view.findViewById(R.id.negative_button);
            dialog_title = view.findViewById(R.id.dialog_title);
            dialog_message = view.findViewById(R.id.dialog_message);
            positivebutton.setVisibility(View.GONE);
            negativebutton.setVisibility(View.GONE);

            dialog_title.setText("Félicitation");
            dialog_message.setText("Bravo! Vous avez eu la note complete! \n" +
                    "Maintenant, vous avez l'option d'ajouter une nouvelle question!");


            felicitation.setCanceledOnTouchOutside(false);
            felicitation.setContentView(view);
            felicitation.show();
             new Handler().postDelayed(new Runnable() {
                public void run() {
                    felicitation.dismiss();
                }
            }, 3000);

            AlertDialog.Builder Felicitation = new android.app.AlertDialog.Builder(this);
            Felicitation.setTitle("Félicitation")
                    .setMessage("Bravo! Vous avez eu la note complete! \n" +
                            "Maintenant, vous avez l'option d'ajouter une nouvelle question!");




            addQuestion.setVisibility(View.VISIBLE);

            addQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(ShowCorrection.this, DBManager.class);
                    t.putExtra("invoker", "ShowCorrection");
                    startActivity(t);
                }
            });
        }


        //Return to MainMenu



        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMenu = new Intent(ShowCorrection.this, MainMenu.class);
                backMenu.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                backMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backMenu);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        returnMain.performClick();
    }

    public static void isAdded(){
        addQuestion.setVisibility(View.GONE);
    }

    public void onViewCorrectAnswerClick(View v){
        Intent t = new Intent(ShowCorrection.this, Session.class);

        t.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        t.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        t.putExtra("minutes",getIntent().getLongExtra("minutes",0));
        t.putExtra("seconds",getIntent().getLongExtra("seconds",0));
        t.putExtra("Showcorrection",1);
        startActivity(t);
    }
}
