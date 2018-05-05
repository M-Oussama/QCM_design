package com.univ_setif.fsciences.qcm.control;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.fragments.RecyclerViewFragment;

import org.w3c.dom.Text;

/**
 * Created by oussama on 04/05/2018.
 */

public class QcmRecyclerAdapter extends RecyclerView.Adapter<QcmRecyclerAdapter.MyViewHolder> {

    Context context;
    public QcmRecyclerAdapter(Context context){
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qcm_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        mcqCTRL controleur = new mcqCTRL(context,null);
        if(position<=controleur.getDatabasesCount()){


            final String key = controleur.getDatabaseData().keySet().toArray()[position].toString();
            final String value = controleur.getDatabaseData().get(key);
            holder.Qcm_number.setText(String.valueOf(position+1));
            holder.Qcm_name.setText(String.valueOf(value));
            holder.dbName = key;
            holder.cardviewqcm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoquestion = new Intent(context, RecyclerViewFragment.class);
                    gotoquestion.putExtra("Qcmposition",position+1);
                    gotoquestion.putExtra("Qcmfullname",value);
                    gotoquestion.putExtra("dbname",key);
                    gotoquestion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(gotoquestion);
        }




        });

    }
}

    @Override
    public int getItemCount() {
        return new mcqCTRL(context,null).getDatabasesCount();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
       public TextView Qcm_number,Qcm_name,update,delete;
       public CardView cardviewqcm;
       public RelativeLayout view_background;
       public CardView view_foreground;
       public String dbName;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardviewqcm= itemView.findViewById(R.id.cardviewqcm);
            Qcm_number =itemView.findViewById(R.id.Qcm_position);
            Qcm_name = itemView.findViewById(R.id.Qcm_title);
            view_background = itemView.findViewById(R.id.background);
            view_foreground = itemView.findViewById(R.id.cardviewqcm);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    public void delete(final Context context, final String dbName,final int position){
         AlertDialog.Builder deleteQCM = new AlertDialog.Builder(context);
        deleteQCM.setMessage("you want to delete this QCM .?")
                .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mcqCTRL controlleur = new mcqCTRL(context,dbName);
                        controlleur.deleteDatabase();

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,getItemCount());
                        Toast t = Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT);
                        t.show();

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
        AlertDialog doit= deleteQCM.create();
        doit.show();
    }
}
