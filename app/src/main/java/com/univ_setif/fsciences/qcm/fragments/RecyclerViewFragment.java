package com.univ_setif.fsciences.qcm.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.univ_setif.fsciences.qcm.MCQEditor;
import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.control.RecyclerAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;

import java.util.ArrayList;

/**
 * Created by oussama on 18/04/2018.
 */

public class RecyclerViewFragment extends AppCompatActivity {
    public RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    TextView Qcmfullname,Qcmposition,Questionnumber;
    public ArrayList<QCM> qcmArrayList;
    com.github.clans.fab.FloatingActionMenu menufab;
    com.github.clans.fab.FloatingActionButton newQuizfab,
                                              sessionsettingsfab;
    int position=0;
    private ArrayList<QCM> qcm;
    public Context context;
    public RecyclerView.LayoutManager layoutManager;
    private  String dbfullname,dbname;

    public RecyclerViewFragment(){

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recyclerview);

        context = getApplicationContext();

        initcomponent();
        dbfullname = getIntent().getStringExtra("Qcmfullname");
        dbname= getIntent().getStringExtra("dbname");
        int qcmposition = getIntent().getIntExtra("Qcmposition",0);
         Qcmfullname.setText(dbfullname);
         Qcmposition.setText("QCM Number:"+String.valueOf(qcmposition));
          qcmArrayList = getALLQuestion(dbname);
         if(qcmArrayList ==null){
             Questionnumber.setText("Question:"+0);
         }else{
             Questionnumber.setText("Question:"+String.valueOf(qcmArrayList.size()));
         }

         recyclerAdapter = new RecyclerAdapter(position, qcmArrayList,getApplicationContext(),recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false ));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);



        // ADMIN MENU ITEMS

        // ADD NEW QUIZ
        newQuizfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), MCQEditor.class);
                add.putExtra("AddQuestion",true);
                add.putExtra("dbname",dbname);
                startActivityForResult(add, 0);

            }
        });


        //SESSION SETTINGS
        sessionsettingsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdvancedOptionsClick();
            }
        });



          new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

               if(direction==ItemTouchHelper.LEFT){

                   //SWIPE LEFT TO DELETE QUESTION
                   delete(viewHolder.getAdapterPosition(), (RecyclerAdapter.ViewHolder) viewHolder, dbname,RecyclerViewFragment.this);
                   //qcmArrayList.remove(viewHolder.getAdapterPosition());
               } else{
                   //  Swipe RIGHT TO UPDATE QUESTION
                   ArrayList<Answer> answers ;
                   // MAKE DELETE ICONS INVISIBLE
                   View delete = ((RecyclerAdapter.ViewHolder)viewHolder).delete;
                   delete.setVisibility(View.INVISIBLE);

                   View background = ((RecyclerAdapter.ViewHolder)viewHolder).background;
                   background.setBackgroundColor(getResources().getColor(R.color.green));


                   answers =getAnswers(((RecyclerAdapter.ViewHolder) viewHolder).questioncontent.getText().toString(), qcmArrayList);



                   Intent update = new Intent(getApplicationContext(),MCQEditor.class);
                   update.putExtra("MCQEditor",true);
                   Bundle Questioninfo =new Bundle();
                   Questioninfo.putString("OldQuestion",((RecyclerAdapter.ViewHolder) viewHolder).questioncontent.getText().toString());
                   Questioninfo.putString("OldAnswer1",answers.get(0).getText());
                   Questioninfo.putString("OldAnswer2",answers.get(1).getText());
                   Questioninfo.putString("OldAnswer3",answers.get(2).getText());
                   Questioninfo.putString("OldAnswer4",answers.get(3).getText());
                   Questioninfo.putString("dbname", dbname);

                   update.putExtra("CorrectAnswer", qcmArrayList.get(Integer.parseInt(answers.get(4).getText())).getQuestion().getAnswers());
                   update.putExtras(Questioninfo);
                   startActivityForResult(update, 0);

               }


            }

               @Override
               public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if(viewHolder!=null)
                {
                    View foreground1 = ((RecyclerAdapter.ViewHolder)viewHolder).foreground;
                    getDefaultUIUtil().onSelected(foreground1);
                }
               }

               @Override
               public int convertToAbsoluteDirection(int flags, int layoutDirection) {
                   return super.convertToAbsoluteDirection(flags, layoutDirection);
               }

               @Override
               public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                 View foreground1 = ((RecyclerAdapter.ViewHolder)viewHolder).foreground;
                 getDefaultUIUtil().clearView(foreground1);
               }

               @Override
               public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                   if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                       View background = ((RecyclerAdapter.ViewHolder)viewHolder).background;

                       if (dX > 0) {

                           View update = ((RecyclerAdapter.ViewHolder)viewHolder).update;
                           update.setVisibility(View.VISIBLE);
                           View delete = ((RecyclerAdapter.ViewHolder)viewHolder).delete;
                           delete.setVisibility(View.INVISIBLE);
                           background.setBackgroundColor(getResources().getColor(R.color.green));
                       }else{
                           View delete = ((RecyclerAdapter.ViewHolder)viewHolder).delete;
                           delete.setVisibility(View.VISIBLE);
                           View update = ((RecyclerAdapter.ViewHolder)viewHolder).update;
                           update.setVisibility(View.INVISIBLE);
                           background.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                       }
                   }else{}

                   View foreground1 = ((RecyclerAdapter.ViewHolder)viewHolder).foreground;
                   getDefaultUIUtil().onDraw(c,recyclerView,foreground1,dX,dY,actionState,isCurrentlyActive);
               }

               @Override
               public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                   View foreground1 = ((RecyclerAdapter.ViewHolder)viewHolder).foreground;

                   getDefaultUIUtil().onDrawOver(c,recyclerView,foreground1,dX,dY,actionState,isCurrentlyActive);

               }
           }).attachToRecyclerView(recyclerView);




    }

    private ArrayList<QCM> getALLQuestion(String dbname) {

        mcqCTRL controleur = new mcqCTRL(getApplicationContext(), dbname);

        controleur.openReadable();
        qcm = (ArrayList<QCM>) controleur.getAllQCM();
        controleur.close();
        return  qcm;
    }

    public void onAdvancedOptionsClick(){
        AlertDialog.Builder advBuilder = new AlertDialog.Builder(RecyclerViewFragment.this);

        //inflating layout on view
        @SuppressLint("InflateParams")
        final View editorView = getLayoutInflater().inflate(R.layout.dialog_advanced_options, null);
        advBuilder.setView(editorView);

        //Creating Dialog Popup
        final AlertDialog advancedOptions = advBuilder.create();
        advancedOptions.setCancelable(false);
        advancedOptions.setCanceledOnTouchOutside(true);
        advancedOptions.show();

        final NumberPicker minutes  = editorView.findViewById(R.id.minutes);
        final NumberPicker secondes = editorView.findViewById(R.id.secondes);
        final NumberPicker nbrQCM   = editorView.findViewById(R.id.nbrQCM);

        Button save = editorView.findViewById(R.id.param_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor adminSettings = getApplicationContext().getSharedPreferences("adminSettings", MODE_PRIVATE).edit();
                adminSettings.putLong("minutes", minutes.getValue());
                adminSettings.putLong("secondes", secondes.getValue());
                adminSettings.putInt("nbrQCM", nbrQCM.getValue());
                adminSettings.apply();
                advancedOptions.cancel();
            }
        });

        minutes.setMaxValue(90);
        minutes.setMinValue(0);

        secondes.setMaxValue(59);
        secondes.setMinValue(0);

        nbrQCM.setMinValue(10);
        nbrQCM.setMaxValue(qcm.size());
    }

    public ArrayList<Answer> getAnswers(String Question, ArrayList<QCM> allitem){
        ArrayList<Answer> Answers = new ArrayList<>();
        for (int i = 0; i <allitem.size() ; i++) {
            if(allitem.get(i).getQuestion().getText().equals(Question)){


                Answers.add(0,allitem.get(i).getAns1());
                Answers.add(1,allitem.get(i).getAns2());
                Answers.add(2,allitem.get(i).getAns3());
                Answers.add(3,allitem.get(i).getAns4());
                Answers.add(4,new Answer(String.valueOf(i)));

            }

        }
        return Answers;

    }


     private void  initcomponent(){
         Qcmfullname = findViewById(R.id.qcmfullname);
         Qcmposition = findViewById(R.id.qcmposition);
         Questionnumber = findViewById(R.id.questionnumber);

        recyclerView = findViewById(R.id.recyclerView);
        menufab=  findViewById(R.id.menufab);
        newQuizfab= findViewById(R.id.New_Quiz);
        sessionsettingsfab= findViewById(R.id.SessionSettings);
    }

     private void LogOut(){
        AlertDialog.Builder logoutconfirmation = new AlertDialog.Builder(getApplicationContext());
        logoutconfirmation.setTitle("LOG OUT")
                .setMessage("Voulez-vous vraiment revenir au menu principale ")
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog logout = logoutconfirmation.create();
        logout.show();
    }

    public void delete(final int position, final RecyclerAdapter.ViewHolder viewHolder, final String dbname, Context mcontext){
        android.support.v7.app.AlertDialog.Builder confirm = new android.support.v7.app.AlertDialog.Builder(mcontext);
        confirm.setMessage("Voulez-vous vraiment supprimer ce QCM? Cette opération est irréversible")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mcqCTRL controleur = new mcqCTRL(getApplicationContext(), getIntent().getStringExtra("dbname"));
                        controleur.openWritable();
                        controleur.deleteQCM(new Question(viewHolder.questioncontent.getText().toString()));
                        //qcmArrayList = (ArrayList<QCM>) controleur.getAllQCM();
                        controleur.close();
                        qcmArrayList.remove(viewHolder.getAdapterPosition());
                        recyclerAdapter.notifyItemRemoved(position);
                        recyclerAdapter.notifyItemRangeChanged(position,qcmArrayList.size());
                        Questionnumber.setText("Question:" + qcmArrayList.size());
                        Toast t = Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT);
                        t.show();


                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        recyclerAdapter.notifyDataSetChanged();
                        dialogInterface.cancel();
                    }
                });


        android.support.v7.app.AlertDialog deleteDialog = confirm.create();
        deleteDialog.setCanceledOnTouchOutside(true);

        deleteDialog.setTitle("Supprimer");
        deleteDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0)
            if(resultCode == -1)
                if (data.getBooleanExtra("changed", false)) {
                    qcmArrayList = getALLQuestion(dbname);
                    recyclerAdapter = new RecyclerAdapter(position, qcmArrayList, getApplicationContext(), recyclerView);
                    recyclerView.setAdapter(recyclerAdapter);
                    Questionnumber.setText("Question:" + qcmArrayList.size());
                    recyclerView.invalidate();
                }

    }
}
