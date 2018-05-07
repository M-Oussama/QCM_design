package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.Main.AdminSpace;
import com.univ_setif.fsciences.qcm.control.QcmRecyclerAdapter;
import com.univ_setif.fsciences.qcm.control.mcqCTRL;

public class MCQManager extends Activity {
    int pageCount = 1;
    RecyclerView recyclerView;
    public static Context context;
    private String pagetitle[];
    QcmRecyclerAdapter qcmRecyclerAdapter;
    TextView Qcmnbr;

    com.github.clans.fab.FloatingActionButton logout,newQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mcqmanage);
        AdminSpace.password.getEditText().setText("");


        recyclerView = findViewById(R.id.Qcm_recyclerview);
        qcmRecyclerAdapter = new QcmRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(qcmRecyclerAdapter);
        Qcmnbr = findViewById(R.id.qcm_nbr);
        Qcmnbr.setText("Module : "+new mcqCTRL(getApplicationContext(),null).getDatabasesCount());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false ));
       /* Intent gotoquestion = new Intent(getApplicationContext(),RecyclerViewFragment.class);
        startActivity(gotoquestion);
        finish();*/
       logout = findViewById(R.id.logout);
       newQuiz = findViewById(R.id.New_Quiz_Subject);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               LogOut();
           }
       });
        logout.setColorPressed(getResources().getColor(android.R.color.holo_blue_bright));
        newQuiz.setColorPressed(getResources().getColor(android.R.color.holo_blue_bright));


       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               TextView qcm_name = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).Qcm_name;
               String dbName = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).dbName;

               if(direction==ItemTouchHelper.LEFT){

                   qcmRecyclerAdapter.delete(MCQManager.this, dbName, viewHolder.getAdapterPosition(),Qcmnbr,getLayoutInflater());

               }else{

                   Intent updateQcm = new Intent(MCQManager.this,DBManager.class);
                   updateQcm.putExtra("Qcmfullname",qcm_name.getText().toString());
                   updateQcm.putExtra("QcmDbName", dbName);
                   updateQcm.putExtra("update",true);
                   startActivityForResult(updateQcm, 1);


               }
           }
           @Override
           public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
               if(viewHolder!=null)
               {
                   View foreground1 = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).view_foreground;
                   getDefaultUIUtil().onSelected(foreground1);
               }
           }

           @Override
           public int convertToAbsoluteDirection(int flags, int layoutDirection) {
               return super.convertToAbsoluteDirection(flags, layoutDirection);
           }

           @Override
           public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
               View foreground1 = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).view_foreground;
               getDefaultUIUtil().clearView(foreground1);
           }

           @Override
           public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
               if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                   View background = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).view_background;

                   if (dX > 0) {

                       View update = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).update;
                       update.setVisibility(View.VISIBLE);
                       View delete = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).delete;
                       delete.setVisibility(View.INVISIBLE);
                       background.setBackgroundColor(getResources().getColor(R.color.green));
                   }else{
                       View delete = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).delete;
                       delete.setVisibility(View.VISIBLE);
                       View update = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).update;
                       update.setVisibility(View.INVISIBLE);
                       background.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                   }
               }else{}

               View foreground1 = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).view_foreground;
               getDefaultUIUtil().onDraw(c,recyclerView,foreground1,dX,dY,actionState,isCurrentlyActive);
           }

           @Override
           public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
               View foreground1 = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).view_foreground;

               getDefaultUIUtil().onDrawOver(c,recyclerView,foreground1,dX,dY,actionState,isCurrentlyActive);

           }
       }).attachToRecyclerView(recyclerView);

    }

    public void onAddNewSubjectClick(View v){
        Intent addQuizsubject = new Intent(MCQManager.this, DBManager.class);
        addQuizsubject.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(addQuizsubject);

    }

    private void LogOut(){



        final Dialog logoutconfirmation = new Dialog(MCQManager.this);

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.alertdialog,null);

        Button positivebutton,negativebutton;
        TextView dialog_title,dialog_message;

        positivebutton =view.findViewById(R.id.positivebutton);
        negativebutton =view.findViewById(R.id.negative_button);
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_message = view.findViewById(R.id.dialog_message);

        Typeface gunnyRewritten = Typeface.createFromAsset(MCQManager.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
        positivebutton.setTypeface(gunnyRewritten);
        negativebutton.setTypeface(gunnyRewritten);
        dialog_title.setTypeface(gunnyRewritten);
        dialog_message.setTypeface(gunnyRewritten);

        dialog_title.setText("LOG OUT");
        dialog_message.setText("Voulez-vous vraiment revenir au menu principale ");

        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        negativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutconfirmation.cancel();

            }
        });
        logoutconfirmation. setCanceledOnTouchOutside(false);
        logoutconfirmation.setContentView(view);
        logoutconfirmation.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            qcmRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {

        final Dialog logoutconfirmation = new Dialog(MCQManager.this);

        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.alertdialog,null);

        Button positivebutton,negativebutton;
        TextView dialog_title,dialog_message;

        positivebutton =view.findViewById(R.id.positivebutton);
        negativebutton =view.findViewById(R.id.negative_button);
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_message = view.findViewById(R.id.dialog_message);
        Typeface gunnyRewritten = Typeface.createFromAsset(MCQManager.this.getApplicationContext().getAssets(), "fonts/gnyrwn971.ttf");
        positivebutton.setTypeface(gunnyRewritten);
        negativebutton.setTypeface(gunnyRewritten);
        dialog_title.setTypeface(gunnyRewritten);
        dialog_message.setTypeface(gunnyRewritten);
        dialog_title.setText("LOG OUT");
        dialog_message.setText("Voulez-vous vraiment revenir au menu principale ");

        positivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        negativebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logoutconfirmation.cancel();

            }
        });
        logoutconfirmation. setCanceledOnTouchOutside(false);
        logoutconfirmation.setContentView(view);
        logoutconfirmation.show();


    }

}
