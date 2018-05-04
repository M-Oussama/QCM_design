package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class DBmanager extends Activity {

    Button Addmcq,updatemcq;
    EditText dbNameEditText;
    EditText fullNameEditText;
    String  pagetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmcq);

        //init component
        Addmcq           = findViewById(R.id.addmcq);
        updatemcq = findViewById(R.id.updatemcq);
        dbNameEditText   = findViewById(R.id.mcq_name);
        fullNameEditText = findViewById(R.id.mcq_desc);

        if(getIntent().getBooleanExtra("update",false))
        {
            Addmcq.setVisibility(View.GONE);
            updatemcq.setVisibility(View.VISIBLE);
            final String dbname = new mcqCTRL(DBmanager.this,null).getDatabaseData().get(getIntent().getStringExtra("Qcmfullname"));
             dbNameEditText.setText(dbname);
             fullNameEditText.setText(getIntent().getStringExtra("Qcmfullname"));
             updatemcq.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     mcqCTRL mcqCTRL =new mcqCTRL(DBmanager.this,dbname);
                     mcqCTRL.editDatabaseName(dbname);
                     Intent backtoQCM = new Intent(DBmanager.this,MCQManager.class);
                     startActivity(backtoQCM);
                 }
             });

        }else  {
            Addmcq.setVisibility(View.VISIBLE);
            updatemcq.setVisibility(View.GONE);
            Addmcq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dbName = dbNameEditText.getText().toString();
                    String fullName = fullNameEditText.getText().toString();
                    if(dbName.isEmpty() || fullName.isEmpty()) {
                        Toast.makeText(DBmanager.this, "Input cannot be left null", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //This line initializes a new database with the name dbName and descirption fullName
                    //it is the line responsible of cteating the database AND its metadata
                    mcqCTRL ctrl = new mcqCTRL(getApplicationContext(), fullName, dbName);
                    ctrl.init();
                    int numb = ctrl.getDatabasesCount();

                    pagetitle=fullName;
                    Toast.makeText(getApplicationContext(), "Added successfully: Nbr = " + numb , Toast.LENGTH_SHORT).show();
                    Intent backtomanger = new Intent(getApplicationContext(),MCQManager.class);
                    backtomanger.putExtra("pagetitle",pagetitle);
                    backtomanger.putExtra("position",numb-1);
                    startActivity(backtomanger);
                    finish();
                }
            });
        }



    }
}
