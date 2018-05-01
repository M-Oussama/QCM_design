package com.univ_setif.fsciences.qcm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowCorrection extends AppCompatActivity {
    Button returnMain;
    static Button addQuestion;
    TextView showNote;
    double note = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_correction);

        //Note Display
        showNote = findViewById(R.id.note);
        note        = getIntent().getExtras().getDouble("note");
        String noteString = note + "/20";
        showNote.setText(noteString);


        //Add Question Button Display
        if(note==20) {

            AlertDialog.Builder Felicitation = new android.app.AlertDialog.Builder(this);
            Felicitation.setTitle("Félicitation")
                    .setMessage("Bravo! Vous avez eu la note complete! \n" +
                            "Maintenant, vous avez l'option d'ajouter une nouvelle question!");


            final AlertDialog felicitation = Felicitation.create();
            felicitation.show();

            Handler handler = null;
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    felicitation.dismiss();
                }
            }, 3000);

            addQuestion =  findViewById(R.id.add_question);

            addQuestion.setVisibility(View.VISIBLE);

            addQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(ShowCorrection.this, RetroMCQEditor.class);
                    t.putExtra("invoker", "ShowCorrection");
                    startActivity(t);
                }
            });
        }


        //Return to MainMenu
        returnMain = findViewById(R.id.back_menu);


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
        startActivity(t);
    }
}
