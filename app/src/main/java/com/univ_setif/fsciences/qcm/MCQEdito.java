package com.univ_setif.fsciences.qcm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.univ_setif.fsciences.qcm.control.RecyclerAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;

import java.util.ArrayList;

public class MCQEdito extends AppCompatActivity {
    Button Add,Update;
    FoldingCell foldingCell;
    EditText question,
             Answer1,
             Answer2,
             Answer3,
             Answer4;
    private CheckBox isCorrect1,
                     isCorrect2,
                     isCorrect3,
                     isCorrect4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        Add = findViewById(R.id.add);
        Update = findViewById(R.id.Update);
        foldingCell = findViewById(R.id.folding_cell);
        question = findViewById(R.id.questiontitle);
        Answer1 = findViewById(R.id.answer1);
        Answer2 = findViewById(R.id.answer2);
        Answer3 = findViewById(R.id.answer3);
        Answer4 = findViewById(R.id.answer4);
        isCorrect1 = findViewById(R.id.isCorrect1);
        isCorrect2 = findViewById(R.id.isCorrect2);
        isCorrect3 = findViewById(R.id.isCorrect3);
        isCorrect4 = findViewById(R.id.isCorrect4);

       if(getIntent().getBooleanExtra("AddQuestion",false)){


           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   foldingCell.toggle(false);
               }
           },400);
           Add.setVisibility(View.VISIBLE);
           Update.setVisibility(View.GONE);




       }else {
           Update.setVisibility(View.VISIBLE);
           Add.setVisibility(View.GONE);



           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   foldingCell.toggle(false);
               }
           },400);
           question.setText(getIntent().getExtras().get("OldQuestion").toString());
           Answer1.setText(getIntent().getExtras().get("OldAnswer1").toString());
           Answer2.setText(getIntent().getExtras().get("OldAnswer2").toString());
           Answer3.setText(getIntent().getExtras().get("OldAnswer3").toString());
           Answer4.setText(getIntent().getExtras().get("OldAnswer4").toString());
           Toast.makeText(this, ""+getIntent().getExtras().get("OldAnswer1"), Toast.LENGTH_SHORT).show();

       }

    }

    public void UPDATE(View view) {
        finish();


    }

    public void ADD(View view) {

        if(!checkInputValidity()) return;
            Answer ans1 = new Answer(Answer1.getText().toString());
            Answer ans2 = new Answer(Answer2.getText().toString());
            Answer ans3 = new Answer(Answer3.getText().toString());
            Answer ans4 = new Answer(Answer4.getText().toString());
            Question qst = new Question(question.getText().toString());

            if(isCorrect1.isChecked())
                qst.setAnswers(ans1);
            if(isCorrect2.isChecked())
                qst.setAnswers(ans2);
            if(isCorrect3.isChecked())
                qst.setAnswers(ans3);
            if(isCorrect4.isChecked())
                qst.setAnswers(ans4);

            //Creating new Database Entry
            mcqCTRL controleur = new mcqCTRL(this, "GL.db");
            controleur.openWritable();

            QCM qcm = new QCM(qst, ans1, ans2, ans3, ans4);
            controleur.createQCM(qcm);
            controleur.close();

            /*if(fromUser)
                ShowCorrection.isAdded();*/

            Toast t = Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
            t.show();

        Intent back = new Intent(this,MCQmanage.class);
        this.startActivity(back);


    }
    private boolean checkInputValidity(){
        if(question.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "Veuillez taper une question", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(Answer1.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 1 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(Answer2.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 2 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(Answer3.getText().toString().isEmpty()){

            Toast t = Toast.makeText(this, "La reponse 3 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(Answer4.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 4 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(!isCorrect1.isChecked() && !isCorrect2.isChecked() && !isCorrect3.isChecked() && !isCorrect4.isChecked()){
            Toast t = Toast.makeText(this, "Veuillez cocher la reponse juste", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        return true;
    }
}
