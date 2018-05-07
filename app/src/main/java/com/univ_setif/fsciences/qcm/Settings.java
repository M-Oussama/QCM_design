package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class Settings extends Activity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    String dbname,dbfullname;
    private int mcqcount;
    private String key,value;
    private  mcqCTRL controleur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        radioGroup =(RadioGroup) findViewById(R.id.radiogroup);

        controleur= new mcqCTRL(getApplicationContext(),null);
        mcqcount = controleur.getDatabasesCount();
        for (int i = 0; i <mcqcount ; i++) {
            radioButton = new RadioButton(this);
            key =controleur.getDatabaseData().keySet().toArray()[i].toString();
            value = controleur.getDatabaseData().get(key);

            radioButton.setText(value);
            radioButton.setId(i);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbname=value;
                    dbfullname = radioButton.getText().toString() ;
                    Toast.makeText(Settings.this, "value : "+" key "+dbname, Toast.LENGTH_SHORT).show();
                }
            });
            radioGroup.addView(radioButton);

        }
        controleur.close();
    }

    public void ChoosedQCM(View view) {
        Intent mcq = new Intent(getApplicationContext(),MainMenu.class);
        mcq.putExtra("dbname",dbname);
        mcq.putExtra("dbfullname",dbfullname);
        startActivity(mcq);
        finish();

    }
}
