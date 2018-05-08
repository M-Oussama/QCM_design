package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;
import com.univ_setif.fsciences.qcm.fragments.RecyclerViewFragment;

import java.util.ArrayList;

public class MCQEditor extends AppCompatActivity {
    Button Add,Update;
    FoldingCell foldingCell;
    EditText question,
             Answer1,
             Answer2,
             Answer3,
             Answer4;
    String dbname;
    boolean update=false;
    private CheckBox isCorrect1,
                     isCorrect2,
                     isCorrect3,
                     isCorrect4;
    private TextView questioncontent,questionnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        Add = findViewById(R.id.add);
        Update = findViewById(R.id.Update);
        foldingCell = findViewById(R.id.folding_cell);
        question = findViewById(R.id.questiontitle);
        questioncontent =findViewById(R.id.questioncontent);
        questionnumber = findViewById(R.id.questionnumber);
        Answer1 = findViewById(R.id.answer1);
        Answer2 = findViewById(R.id.answer2);
        Answer3 = findViewById(R.id.answer3);
        Answer4 = findViewById(R.id.answer4);
        isCorrect1 = findViewById(R.id.isCorrect1);
        isCorrect2 = findViewById(R.id.isCorrect2);
        isCorrect3 = findViewById(R.id.isCorrect3);
        isCorrect4 = findViewById(R.id.isCorrect4);

        question.setVerticalScrollBarEnabled(true);
        question.setMovementMethod(new ScrollingMovementMethod());

        Answer1.setVerticalScrollBarEnabled(true);
        Answer1.setMovementMethod(new ScrollingMovementMethod());

        Answer2.setVerticalScrollBarEnabled(true);
        Answer2.setMovementMethod(new ScrollingMovementMethod());

        Answer3.setVerticalScrollBarEnabled(true);
        Answer3.setMovementMethod(new ScrollingMovementMethod());

        Answer4.setVerticalScrollBarEnabled(true);
        Answer4.setMovementMethod(new ScrollingMovementMethod());

       if(getIntent().getBooleanExtra("AddQuestion",false)|  getIntent().getBooleanExtra("fromuser",false)){


           dbname = getIntent().getStringExtra("dbname");
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   foldingCell.toggle(false);
               }
           },700);
           Add.setVisibility(View.VISIBLE);
           Update.setVisibility(View.GONE);




       }else {
           update=true;
           Update.setVisibility(View.VISIBLE);
           Add.setVisibility(View.GONE);


           dbname = getIntent().getStringExtra("dbname");
           questioncontent.setText(getIntent().getExtras().get("OldQuestion").toString());

          questionnumber.setText(String.valueOf(getIntent().getIntExtra("Questionposition",0)));
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   foldingCell.toggle(false);
               }
           },700);
           question.setText(getIntent().getExtras().get("OldQuestion").toString());
           Answer1.setText(getIntent().getExtras().get("OldAnswer1").toString());
           Answer2.setText(getIntent().getExtras().get("OldAnswer2").toString());
           Answer3.setText(getIntent().getExtras().get("OldAnswer3").toString());
           Answer4.setText(getIntent().getExtras().get("OldAnswer4").toString());
           ArrayList<Answer> correctAnswer = (ArrayList<Answer>) getIntent().getSerializableExtra("CorrectAnswer");


           if(correctAnswer.contains(new Answer(Answer1.getText().toString())))
               isCorrect1.setChecked(true);
           if(correctAnswer.contains(new Answer(Answer2.getText().toString())))
               isCorrect2.setChecked(true);
           if(correctAnswer.contains(new Answer(Answer3.getText().toString())))
               isCorrect3.setChecked(true);
           if(correctAnswer.contains(new Answer(Answer4.getText().toString())))
               isCorrect4.setChecked(true);

       }

    }

    public void UPDATE(View view) {
        if(!checkInputValidity()) return;

        dbname = getIntent().getStringExtra("dbname");

        QCM oldQCM = new QCM(
                new Question(getIntent().getStringExtra("OldQuestion")),
                new Answer(getIntent().getStringExtra("OldAnswer1")),
                new Answer(getIntent().getStringExtra("OldAnswer2")),
                new Answer(getIntent().getStringExtra("OldAnswer3")),
                new Answer(getIntent().getStringExtra("OldAnswer4"))
        );


        Answer ans1 = oldQCM.getAns1();
        Answer ans2 = oldQCM.getAns2();
        Answer ans3 = oldQCM.getAns3();
        Answer ans4 = oldQCM.getAns4();
        Question qst = oldQCM.getQuestion();
        boolean flag = false;

        if(!Answer1.getText().toString().equals(oldQCM.getAns1().getText())) {
            ans1 = new Answer(Answer1.getText().toString());
        }

        if(!Answer2.getText().toString().equals(oldQCM.getAns2().getText())) {
            ans2 = new Answer(Answer2.getText().toString());
        }

        if(!Answer3.getText().toString().equals(oldQCM.getAns3().getText())) {
            ans3 = new Answer(Answer3.getText().toString());
        }

        if(!Answer4.getText().toString().equals(oldQCM.getAns4().getText())) {
            ans4 = new Answer(Answer4.getText().toString());
        }

        if(!question.getText().toString().equals(oldQCM.getQuestion().getText())){
            qst = new Question(question.getText().toString());
        }else
            flag = true;

        if(isCorrect1.isChecked())
            qst.setAnswers(ans1);
        if(isCorrect2.isChecked())
            qst.setAnswers(ans2);
        if(isCorrect3.isChecked())
            qst.setAnswers(ans3);
        if(isCorrect4.isChecked())
            qst.setAnswers(ans4);



        QCM newQcm = new QCM(qst, ans1, ans2, ans3, ans4);

        mcqCTRL controleur = new mcqCTRL(this, dbname);
        controleur.openWritable();
        controleur.updateQCM(oldQCM, newQcm, flag);
        controleur.close();

        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        questioncontent.setText(question.getText().toString());
        getIntent().putExtra("changed", true);
        setResult(Activity.RESULT_OK, getIntent());
        foldingCell.toggle(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent backtomanger = new Intent(getApplicationContext(),RecyclerViewFragment.class);
                backtomanger.putExtra("fromupdate",true);
                backtomanger.putExtra("Questionposition",getIntent().getIntExtra("Questionposition",0));

                backtomanger.putExtra("Qcmfullname", getIntent().getStringExtra("Qcmfullname") );
                backtomanger.putExtra("dbname",getIntent().getStringExtra("dbname"));
                backtomanger.putExtra("Qcmposition",getIntent().getIntExtra("Qcmposition",0));
                startActivity(backtomanger);
                finish();
            }
        },1000);
    }

    public void ADD(View view) {

        if(!checkInputValidity()) return;

          //  dbname = getIntent().getStringExtra("dbname");

        Toast.makeText(this, ""+dbname, Toast.LENGTH_SHORT).show();
            Answer ans1 = new Answer(Answer1.getText().toString());
            Answer ans2 = new Answer(Answer2.getText().toString());
            Answer ans3 = new Answer(Answer3.getText().toString());
            Answer ans4 = new Answer(Answer4.getText().toString());
            Question qst = new Question(question.getText().toString());
        questioncontent.setText(question.getText().toString());
            if(isCorrect1.isChecked())
                qst.setAnswers(ans1);
            if(isCorrect2.isChecked())
                qst.setAnswers(ans2);
            if(isCorrect3.isChecked())
                qst.setAnswers(ans3);
            if(isCorrect4.isChecked())
                qst.setAnswers(ans4);

            //Creating new Database Entry
            mcqCTRL controleur = new mcqCTRL(this, dbname);
            controleur.openWritable();

            QCM qcm = new QCM(qst, ans1, ans2, ans3, ans4);
            controleur.createQCM(qcm);
            controleur.close();
          foldingCell.toggle(false);
           if(getIntent().getBooleanExtra("fromuser",false)){
            final Intent gotomain = new Intent(getApplicationContext(),MainMenu.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(gotomain);
                    finish();
                }
            },800);


        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent backtomanger = new Intent(getApplicationContext(),RecyclerViewFragment.class);


                    backtomanger.putExtra("Qcmfullname", getIntent().getStringExtra("Qcmfullname") );
                    backtomanger.putExtra("dbname",getIntent().getStringExtra("dbname"));
                    backtomanger.putExtra("Qcmposition",getIntent().getIntExtra("Qcmposition",0));

                    backtomanger.putExtra("Questionposition",getIntent().getIntExtra("Questionposition",0));
                    startActivity(backtomanger);
                    finish();
                }
            },800);
        }


            Toast t = Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
            t.show();

           /* getIntent().putExtra("changed", true);
            setResult(RESULT_OK, getIntent());
            finish();*/
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

    @Override
    public void onBackPressed() {
        if(getIntent().getBooleanExtra("fromuser",false)) {
            final Intent gotomain = new Intent(getApplicationContext(), ShowCorrection.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(gotomain);
                    finish();
                }
            }, 800);
        }else{
            final Intent backtoqcm = new Intent(getApplicationContext(),RecyclerViewFragment.class);
            if (update = false)
                backtoqcm.putExtra("fromback",true);


            backtoqcm.putExtra("Qcmfullname",getIntent().getStringExtra("Qcmfullname"));
            backtoqcm.putExtra("dbname",getIntent().getStringExtra("dbname"));
            backtoqcm.putExtra("Qcmposition",getIntent().getIntExtra("Qcmposition",0));
            backtoqcm.putExtra("Questionposition",getIntent().getIntExtra("Questionposition",0));

            foldingCell.toggle(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MCQEditor.super.onBackPressed();
                    startActivity(backtoqcm);
                    finish();
                }
            },800);

        }



    }
}
