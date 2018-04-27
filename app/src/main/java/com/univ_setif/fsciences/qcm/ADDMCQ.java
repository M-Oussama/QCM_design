package com.univ_setif.fsciences.qcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ADDMCQ extends AppCompatActivity {

    Button Addmcq;
    EditText MCQ_name;
    String newmcq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmcq);

        //init component
        Addmcq= findViewById(R.id.addmcq);
        MCQ_name = findViewById(R.id.mcq_name);


        Addmcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MCQ_name.getText().toString().isEmpty())
                    Toast.makeText(ADDMCQ.this, "You Should Enter Name to New MCQ", Toast.LENGTH_SHORT).show();
                else{
                    newmcq = MCQ_name.getText().toString();
                    Toast.makeText(ADDMCQ.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                    finish();

                }
            }
        });

        

    }
}
