package com.univ_setif.fsciences.qcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.control.QcmRecyclerAdapter;

public class MCQManager extends Activity {
    int pageCount = 1;
    RecyclerView recyclerView;
    public static Context context;
    private String pagetitle[];
    QcmRecyclerAdapter qcmRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mcqmanage);



        recyclerView = findViewById(R.id.Qcm_recyclerview);
        qcmRecyclerAdapter = new QcmRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(qcmRecyclerAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false ));
       /* Intent gotoquestion = new Intent(getApplicationContext(),RecyclerViewFragment.class);
        startActivity(gotoquestion);
        finish();*/



       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               TextView qcm_name = ((QcmRecyclerAdapter.MyViewHolder)viewHolder).Qcm_name;
               String dbfullname = qcm_name.getText().toString();

               if(direction==ItemTouchHelper.LEFT){

                   qcmRecyclerAdapter.delete(MCQManager.this,dbfullname,viewHolder.getAdapterPosition());

               }else{

                   Intent updateQcm = new Intent(MCQManager.this,DBmanager.class);
                   updateQcm.putExtra("Qcmfullname",dbfullname);
                   updateQcm.putExtra("update",true);
                   startActivity(updateQcm);


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
    @Override
    public void onBackPressed() {
        AlertDialog.Builder logoutconfirmation = new AlertDialog.Builder(MCQManager.this);
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

}
