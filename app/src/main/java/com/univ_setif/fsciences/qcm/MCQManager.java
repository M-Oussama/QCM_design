package com.univ_setif.fsciences.qcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.univ_setif.fsciences.qcm.control.QCMArrayAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

public class MCQManager extends AppCompatActivity {
    mcqCTRL controleur;

    //bellow API 21 (Lollipop)
    QCMArrayAdapter qcmArrayAdapter;
    ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqmanager);

        controleur     = new mcqCTRL(this);

        displayQuestionList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayQuestionList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        displayQuestionList();
    }


    private void displayQuestionList() {
        ArrayList<QCM> qcm = (ArrayList<QCM>) controleur.getAllQCM();
        qcmArrayAdapter = new QCMArrayAdapter(this, R.layout.activity_mcqeditor_create, qcm);

        list = findViewById(R.id.listMCQ);

        list.setAdapter(qcmArrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                QCM qcm = (QCM) list.getItemAtPosition(position);

                Intent i = new Intent(MCQManager.this, MCQEditor.class);

                i.putExtra("invoker", "mcqmanager");
                i.putExtra("oldQuestionText", qcm.getQuestion().getText());
                i.putExtra("oldAnswerText1", qcm.getAns1().getText());
                i.putExtra("oldAnswerText2", qcm.getAns2().getText());
                i.putExtra("oldAnswerText3", qcm.getAns3().getText());
                i.putExtra("oldAnswerText4", qcm.getAns4().getText());
                i.putExtra("correctAnswer", qcm.getQuestion().getAnswer().getText());

                startActivity(i);
            }
        });
    }

    public void onAddFloatingButtonClick(View v){
        Intent t = new Intent(MCQManager.this, MCQEditor.class);
        t.putExtra("invoker", "addFloat");
        this.startActivity(t);
    }

}
