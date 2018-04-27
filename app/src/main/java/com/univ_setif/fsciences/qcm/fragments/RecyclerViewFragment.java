package com.univ_setif.fsciences.qcm.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.univ_setif.fsciences.qcm.ADDMCQ;
import com.univ_setif.fsciences.qcm.MCQEdito;
import com.univ_setif.fsciences.qcm.MainMenu;
import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.control.RecyclerAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by oussama on 18/04/2018.
 */

public class RecyclerViewFragment extends Fragment {
    public static RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    public ArrayList<QCM> ALLitem;
    com.github.clans.fab.FloatingActionMenu menufab;
    com.github.clans.fab.FloatingActionButton newQuizfab,
                                              newquizsubjectfab,
                                              removequizsubject,
                                              sessionsettingsfab,
                                              logout;
    int position=0;
    private ArrayList<QCM> qcm;
    public static Context context;
    private LinearLayoutManager linearLayoutManager;
    public RecyclerView.LayoutManager layoutManager;

    public RecyclerViewFragment(){

    }

   @SuppressLint("ValidFragment")
   public RecyclerViewFragment(int position){
        context = getActivity();
        this.position = position;
   }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      View view= inflater.inflate(R.layout.fragment_recyclerview, container, false);

        initcomponent(view);
         ALLitem = getALLQuestion();


        linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager= linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(position,ALLitem);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(false);


        // ADMIN MENU ITEMS

        // ADD NEW QUIZ
        newQuizfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getActivity(), MCQEdito.class);
                add.putExtra("AddQuestion",true);
                startActivity(add);

            }
        });
        // ADD NEW QUIZ SUBJECT
        newquizsubjectfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addQuizsubject = new Intent(getActivity(), ADDMCQ.class);
                startActivity(addQuizsubject);
            }
        });
        //DELETE QUIZ SUBJECT

        removequizsubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //SESSION SETTINGS
        sessionsettingsfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdvancedOptionsClick();
            }
        });


         // LOG OUT
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {LogOut();    }
        });




          new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

               if(direction==ItemTouchHelper.LEFT){

                   //SWIPE LEFT TO DELETE QUESTION
                recyclerAdapter.delete(viewHolder.getAdapterPosition(), (RecyclerAdapter.ViewHolder) viewHolder);

               } else{
                   //  Swipe RIGHT TO UPDATE QUESTION
                   ArrayList<String> answers ;
                   // MAKE DELETE ICONS INVISIBLE
                   View delete = ((RecyclerAdapter.ViewHolder)viewHolder).delete;
                   delete.setVisibility(View.INVISIBLE);

                   View background = ((RecyclerAdapter.ViewHolder)viewHolder).background;
                   background.setBackgroundColor(getResources().getColor(R.color.green));


                   answers =getAnswers(((RecyclerAdapter.ViewHolder) viewHolder).questioncontent.getText().toString(),ALLitem);

                   Intent update = new Intent(getActivity(),MCQEdito.class);
                   update.putExtra("MCQEdito",true);
                   Bundle Questioninfo =new Bundle();
                   Questioninfo.putString("OldQuestion",((RecyclerAdapter.ViewHolder) viewHolder).questioncontent.getText().toString());
                   Questioninfo.putString("OldAnswer1",answers.get(0));
                   Questioninfo.putString("OldAnswer2",answers.get(1));
                   Questioninfo.putString("OldAnswer3",answers.get(2));
                   Questioninfo.putString("OldAnswer4",answers.get(3));
                   update.putExtras(Questioninfo);
                   startActivity(update);

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
          HollyViewPagerBus.registerRecyclerView(getActivity(), recyclerView);

        return view;
    }



    private ArrayList<QCM> getALLQuestion() {
        mcqCTRL controleur = new mcqCTRL(getActivity(), "GL.db");

        controleur.openReadable();
        qcm = (ArrayList<QCM>) controleur.getAllQCM();
        controleur.close();
        return  qcm;
    }

     public void onAdvancedOptionsClick(){
        AlertDialog.Builder advBuilder = new AlertDialog.Builder(getActivity());

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
                SharedPreferences.Editor adminSettings = getActivity().getSharedPreferences("adminSettings", MODE_PRIVATE).edit();
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


     public ArrayList<String> getAnswers(String Question,ArrayList<QCM> allitem){
        ArrayList<String> Answers = new ArrayList<>();
        for (int i = 0; i <allitem.size() ; i++) {
            if(allitem.get(i).getQuestion().getText().toString().equals(Question)){

                Answers.add(0,allitem.get(i).getAns1().getText().toString());
                Answers.add(1,allitem.get(i).getAns2().getText().toString());
                Answers.add(2,allitem.get(i).getAns3().getText().toString());
                Answers.add(3,allitem.get(i).getAns4().getText().toString());
            }

        }
        return Answers;

    }


     private void  initcomponent(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        menufab=  view.findViewById(R.id.menufab);
        newQuizfab= view.findViewById(R.id.New_Quiz);
        newquizsubjectfab= view.findViewById(R.id.New_Quiz_Subject);
        removequizsubject= view.findViewById(R.id.Remove_Quiz_Subject);
        sessionsettingsfab= view.findViewById(R.id.SessionSettings);
        logout = view.findViewById(R.id.logout);
    }

     private  void LogOut(){
        AlertDialog.Builder logoutconfirmation = new AlertDialog.Builder(getActivity());
        logoutconfirmation.setTitle("LOG OUT")
                .setMessage("Voulez-vous vraiment revenir au menu principale ")
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent logout = new Intent(getActivity(), MainMenu.class);
                        startActivity(logout);
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

}
