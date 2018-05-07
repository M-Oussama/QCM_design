package com.univ_setif.fsciences.qcm;

import android.app.Activity;
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
    RadioButton radioButton [];
    String dbname,dbfullname;
    TextView titlesettings,changemcq;
    Button choosemcq;
    private int mcqcount;
    private String key,value;
    private  mcqCTRL controleur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        radioGroup =(RadioGroup) findViewById(R.id.radiogroup);
        titlesettings = findViewById(R.id.titlesettings);
        choosemcq = findViewById(R.id.choosemcq);
        changemcq = findViewById(R.id.changemcq);


        Typeface gunnyRewritten = Typeface.createFromAsset(Settings.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
        titlesettings.setTypeface(gunnyRewritten);
        choosemcq.setTypeface(gunnyRewritten);
        changemcq.setTypeface(gunnyRewritten);

        SharedPreferences  getuserchoice = getSharedPreferences("userchoice",MODE_PRIVATE);

        controleur= new mcqCTRL(getApplicationContext(),null);
        mcqcount = controleur.getDatabasesCount();
        radioButton = new RadioButton[mcqcount];
        for (int i = 0; i <mcqcount ; i++) {

            radioButton[i]=new RadioButton(this);
            key =controleur.getDatabaseData().keySet().toArray()[i].toString();
            value = controleur.getDatabaseData().get(key);
            radioButton[i].setTypeface(gunnyRewritten);
            radioButton[i].setText(value);
            radioButton[i].setId(i);

            radioButton[i].setTextSize(25);



            final int finalI = i;
            radioButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbname=controleur.getDatabaseData().keySet().toArray()[radioButton[finalI].getId()].toString();
                    dbfullname = radioButton[finalI].getText().toString() ;
                    Toast.makeText(Settings.this, "dbname "+dbname+" dbfullname "+dbfullname +" i = "+ finalI, Toast.LENGTH_SHORT).show();
                }
            });

            radioGroup.addView(radioButton[i]);


        }

        controleur.close();

        SharedPreferences firstlunch = getSharedPreferences("firstlunch",MODE_PRIVATE);
        getuserchoice = getSharedPreferences("userchoice", MODE_PRIVATE);
        for (int i = 0; i <mcqcount ; i++) {
            if(radioButton[i].getText().toString().equals("Génie Logiciel")&& firstlunch.getBoolean("MMfirstLunch",false)){
                radioButton[i].setChecked(true);
            }else{
                if(radioButton[i].getText().toString().equals(getuserchoice.getString("dbfullname",null))){
                    radioButton[i].setChecked(true);
                }
            }

        }
    }

    public void ChoosedQCM(View view) {
        Intent mcq = new Intent(getApplicationContext(),MainMenu.class);

        SharedPreferences.Editor userchoice = MainMenu.context.getSharedPreferences("userchoice", MODE_PRIVATE).edit();

        userchoice.putString("dbname",dbname);
        userchoice.putString("dbfullname",dbfullname);

        userchoice.commit();
        startActivity(mcq);
        finish();

    }

    public void Chooseqcm(View view) {
      //  dbname=controleur.getDatabaseData().keySet().toArray()[radioButton.getId()].toString();
        //dbfullname = radioButton.getText().toString() ;
    }
}
