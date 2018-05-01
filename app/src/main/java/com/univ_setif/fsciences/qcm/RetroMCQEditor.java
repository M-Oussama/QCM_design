package com.univ_setif.fsciences.qcm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;

import java.util.ArrayList;

public class RetroMCQEditor extends AppCompatActivity {
    private EditText question;

    private CheckBox isCorrect1;
    private CheckBox isCorrect2;
    private CheckBox isCorrect3;
    private CheckBox isCorrect4;

    private EditText answer1;
    private EditText answer2;
    private EditText answer3;
    private EditText answer4;

    private Intent intent;

    private boolean fromUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         intent= getIntent();

         if(intent.getStringExtra("invoker").equals("mcqmanager")) {
             setContentView(R.layout.activity_mcqeditor_update);
             question = findViewById(R.id.question);
             question.setText(intent.getStringExtra("oldQuestionText"));

             answer1 = findViewById(R.id.answer1);
             answer1.setText(intent.getStringExtra("oldAnswerText1"));


             answer2 = findViewById(R.id.answer2);
             answer2.setText(intent.getStringExtra("oldAnswerText2"));


             answer3 = findViewById(R.id.answer3);
             answer3.setText(intent.getStringExtra("oldAnswerText3"));


             answer4 = findViewById(R.id.answer4);
             answer4.setText(intent.getStringExtra("oldAnswerText4"));

             isCorrect1 = findViewById(R.id.isCorrect1);
             isCorrect2 = findViewById(R.id.isCorrect2);
             isCorrect3 = findViewById(R.id.isCorrect3);
             isCorrect4 = findViewById(R.id.isCorrect4);

             @SuppressWarnings("unchecked")
             ArrayList<Answer> correctAnswer = (ArrayList<Answer>) intent.getSerializableExtra("correctAnswer");

             if(correctAnswer.contains(new Answer(answer1.getText().toString())))
                 isCorrect1.setChecked(true);
             if(correctAnswer.contains(new Answer(answer2.getText().toString())))
                 isCorrect2.setChecked(true);
             if(correctAnswer.contains(new Answer(answer3.getText().toString())))
                 isCorrect3.setChecked(true);
             if(correctAnswer.contains(new Answer(answer4.getText().toString())))
                 isCorrect4.setChecked(true);
         }
         else {
             setContentView(R.layout.activity_mcqeditor_create);
             if(intent.getStringExtra("invoker").equals("ShowCorrection"))
                 fromUser = true;

         }

    }

    public void onAddButtonClick(View v){

        initComponents();

        if(!checkInputValidity()) return;

        //Initializing Objects
        Answer ans1 = new Answer(answer1.getText().toString());
        Answer ans2 = new Answer(answer2.getText().toString());
        Answer ans3 = new Answer(answer3.getText().toString());
        Answer ans4 = new Answer(answer4.getText().toString());
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

        if(fromUser)
            ShowCorrection.isAdded();

        Toast t = Toast.makeText(this, "Success", Toast.LENGTH_SHORT);
        t.show();

        finish();
    }

    public void onUpdateButtonClick(View v){
        initComponents();
        if(!checkInputValidity()) return;

        QCM oldQCM = new QCM(
            new Question(intent.getStringExtra("oldQuestionText")),
                new Answer(intent.getStringExtra("oldAnswerText1")),
                new Answer(intent.getStringExtra("oldAnswerText2")),
                new Answer(intent.getStringExtra("oldAnswerText3")),
                new Answer(intent.getStringExtra("oldAnswerText4"))
        );


        Answer ans1 = oldQCM.getAns1();
        Answer ans2 = oldQCM.getAns2();
        Answer ans3 = oldQCM.getAns3();
        Answer ans4 = oldQCM.getAns4();
        Question qst = oldQCM.getQuestion();
        boolean flag = false;

        if(!answer1.getText().toString().equals(oldQCM.getAns1().getText())) {
            ans1 = new Answer(answer1.getText().toString());
        }

        if(!answer2.getText().toString().equals(oldQCM.getAns2().getText())) {
            ans2 = new Answer(answer2.getText().toString());
        }

        if(!answer3.getText().toString().equals(oldQCM.getAns3().getText())) {
            ans3 = new Answer(answer3.getText().toString());
        }

        if(!answer4.getText().toString().equals(oldQCM.getAns4().getText())) {
            ans4 = new Answer(answer4.getText().toString());
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

        mcqCTRL controleur = new mcqCTRL(this, "GL.db");
        controleur.openWritable();
        controleur.updateQCM(oldQCM, newQcm, flag);
        controleur.close();

        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

        finish();

    }

    public void onDeleteButtonClick(View v){
        final String text = intent.getStringExtra("oldQuestionText");

        AlertDialog.Builder confirm = new AlertDialog.Builder(RetroMCQEditor.this);
        confirm.setMessage("Voulez-vous vraiment supprimer ce QCM? Cette opération est irréversible")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      initComponents();
                        mcqCTRL controleur = new mcqCTRL(RetroMCQEditor.this, "GL.db");
                        controleur.openWritable();
                        controleur.deleteQCM(new Question(text));
                        controleur.close();

                        Toast t = Toast.makeText(RetroMCQEditor.this, "Deleted Successfully", Toast.LENGTH_SHORT);
                        t.show();

                        RetroMCQEditor.this.finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog deleteDialog = confirm.create();
        deleteDialog.setCanceledOnTouchOutside(true);
        deleteDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                dialogInterface.cancel();
                return true;
            }
        });
        deleteDialog.setTitle("Supprimer");
        deleteDialog.show();
    }

    private void initComponents(){
        question      = findViewById(R.id.question);

        isCorrect1 = findViewById(R.id.isCorrect1);
        isCorrect2 = findViewById(R.id.isCorrect2);
        isCorrect3 = findViewById(R.id.isCorrect3);
        isCorrect4 = findViewById(R.id.isCorrect4);

        answer1       = findViewById(R.id.answer1);
        answer2       = findViewById(R.id.answer2);
        answer3       = findViewById(R.id.answer3);
        answer4       = findViewById(R.id.answer4);
    }

    private boolean checkInputValidity(){
        if(question.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "Veuillez taper une question", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(answer1.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 1 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(answer2.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 2 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(answer3.getText().toString().isEmpty()){
            Toast t = Toast.makeText(this, "La reponse 3 est vide", Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        if(answer4.getText().toString().isEmpty()){
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
