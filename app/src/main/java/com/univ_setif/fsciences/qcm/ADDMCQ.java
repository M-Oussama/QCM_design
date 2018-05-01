package com.univ_setif.fsciences.qcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class ADDMCQ extends AppCompatActivity {

    Button Addmcq;
    EditText dbNameEditText;
    EditText fullNameEditText;
    String newmcq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmcq);

        //init component
        Addmcq           = findViewById(R.id.addmcq);
        dbNameEditText   = findViewById(R.id.mcq_name);
        fullNameEditText = findViewById(R.id.mcq_desc);


        Addmcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = dbNameEditText.getText().toString();
                String fullName = fullNameEditText.getText().toString();
                if(dbName.isEmpty() || fullName.isEmpty()) {
                    Toast.makeText(ADDMCQ.this, "Input cannot be left null", Toast.LENGTH_SHORT).show();
                    return;
                }

                //This line initializes a new database with the name dbName and descirption fullName
                    //it is the line responsible of cteating the database AND its metadata
                new mcqCTRL(getApplicationContext(), dbName, fullName).init();

                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
