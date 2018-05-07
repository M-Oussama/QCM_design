package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.control.mcqCTRL;

import java.util.Set;

public class Settings extends Activity {

    RadioGroup radioGroup;
    myRadioButton radioButton;
    String dbname,dbfullname;
    private int mcqcount;
    private String key,value;
    private  mcqCTRL controleur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        radioGroup =(RadioGroup) findViewById(R.id.radiogroup);

        Typeface gunnyRewritten = Typeface.createFromAsset(Settings.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");

        ((TextView) findViewById(R.id.title)).setTypeface(gunnyRewritten);
        ((TextView) findViewById(R.id.set_title)).setTypeface(gunnyRewritten);


        controleur= new mcqCTRL(getApplicationContext(),null);
        mcqcount = controleur.getDatabasesCount();
        for (int i = 0; i <mcqcount ; i++) {
            radioButton = new myRadioButton(this);
            radioButton.setTypeface(gunnyRewritten);
            radioButton.setTextSize(18);
            key =controleur.getDatabaseData().keySet().toArray()[i].toString();
            value = controleur.getDatabaseData().get(key);

            radioButton.setText(value);
            radioButton.setDbName(key);
            radioButton.setId(i);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myRadioButton RadioButton = (myRadioButton) v;
                    dbname= RadioButton.getDbName();
                }
            });

            radioGroup.addView(radioButton);
        }
        controleur.close();
    }

    public void ChoosedQCM(View view) {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        userInfo.edit().putString("module", dbname).apply();
        finish();
    }

    private class myRadioButton extends android.support.v7.widget.AppCompatRadioButton{


        private String dbName;

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }


        public myRadioButton(Context context) {
            super(context);
        }


    }
}
