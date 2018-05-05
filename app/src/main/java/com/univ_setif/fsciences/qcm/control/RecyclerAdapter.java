package com.univ_setif.fsciences.qcm.control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ramotion.foldingcell.FoldingCell;
import com.univ_setif.fsciences.qcm.MCQManager;
import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.entity.Answer;
import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;
import com.univ_setif.fsciences.qcm.fragments.RecyclerViewFragment;

import java.util.ArrayList;

/**
 * Created by oussama on 18/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_CELL = 1;
    int Qcmposition =0;
    int posit=0;
    int three=3,one=1,ten=10;
    public ArrayList<QCM> ALLitem;
    Context context;
    RecyclerView recyclerView;



    public RecyclerAdapter(int position, ArrayList<QCM> ALlitem,Context context,RecyclerView recyclerView){
        this.Qcmposition = position;
        this.ALLitem = ALlitem;
        this.context=context;
        this.recyclerView=recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0: return TYPE_HEADER;
            default: return TYPE_CELL;
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View view;
    /*    switch (type){
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hvp_header_placeholder,viewGroup,false);
                break;*/
        //default:
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_update,viewGroup,false);

        //      break;
        //}
        return new RecyclerAdapter.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        for (int i = 0; i <new mcqCTRL(context,null).getDatabasesCount() ; i++) {
            if(Qcmposition==i){
                if(position<=ALLitem.size()){
                    QCM current =ALLitem.get(position);



                    holder.questioncontent.setText(String.valueOf(current.getQuestion().getText().toString()));
                    holder.questionnumber.setText(String.valueOf(position+1));
                }
            }
        }









        holder.cardViewq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.background.setVisibility(View.INVISIBLE);
                displayQuestion(holder,position,ALLitem);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.background.setVisibility(View.VISIBLE);
                    }
                },2000);


                holder.foldingCell.toggle(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.background.setVisibility(View.INVISIBLE);
                        holder.foldingCell.toggle(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.background.setVisibility(View.VISIBLE);

                            }
                        },1000);

                    }
                },5000);



            }
        });




    }

    @Override
    public int getItemCount() {
        if(ALLitem==null){
            return 0;
        }else{
            return  ALLitem.size();
        }


        }






      public class ViewHolder extends  RecyclerView.ViewHolder{

        CardView cardViewq ;
        FoldingCell foldingCell;
        Button button;
        public RelativeLayout background,card;
        public LinearLayout foreground;


        public TextView isCorrect1,
                        isCorrect2,
                        isCorrect3,
                        isCorrect4;

      public  TextView questioncontent,
                       questionnumber,
                       delete,
                       update,
                       questiontitle,
                       Answer1,
                       Answer2,
                       Answer3,
                       Answer4;
        private ViewHolder(View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            cardViewq =itemView.findViewById(R.id.cardviewquestion);
            questioncontent = itemView.findViewById(R.id.questioncontent);
            questionnumber = itemView.findViewById(R.id.questionnumber);
            background = itemView.findViewById(R.id.view_background);
            foreground = itemView.findViewById(R.id.view_foreground);
            card = itemView.findViewById(R.id.card);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);

            questiontitle= itemView.findViewById(R.id.questiontitle);
            Answer1= itemView.findViewById(R.id.answer1);
            Answer2= itemView.findViewById(R.id.answer2);
            Answer3= itemView.findViewById(R.id.answer3);
            Answer4= itemView.findViewById(R.id.answer4);

            isCorrect1 = itemView.findViewById(R.id.isCorrect1);
            isCorrect2 = itemView.findViewById(R.id.isCorrect2);
            isCorrect3 = itemView.findViewById(R.id.isCorrect3);
            isCorrect4 = itemView.findViewById(R.id.isCorrect4);

        }
    }
      public void delete(final int position, final ViewHolder viewHolder, final String dbname,Context mcontext){
       AlertDialog.Builder confirm = new AlertDialog.Builder(mcontext);
       confirm.setMessage("Voulez-vous vraiment supprimer ce QCM? Cette opération est irréversible")
               .setCancelable(false)
               .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       mcqCTRL controleur = new mcqCTRL(context, dbname);
                       controleur.openWritable();
                       Toast.makeText(context, ""+viewHolder.questioncontent.getText(), Toast.LENGTH_SHORT).show();
                       controleur.deleteQCM(new Question(viewHolder.questioncontent.getText().toString()));
                       ALLitem = (ArrayList<QCM>) controleur.getAllQCM();
                       controleur.close();
                       notifyItemRemoved(position);
                       notifyItemRangeChanged(position,getItemCount());
                       Toast t = Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT);
                       t.show();


                   }
               })
               .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       notifyDataSetChanged();
                       dialogInterface.cancel();
                   }
               });


       AlertDialog deleteDialog = confirm.create();
       deleteDialog.setCanceledOnTouchOutside(true);

       deleteDialog.setTitle("Supprimer");
       deleteDialog.show();
    }

      @SuppressLint({"ResourceAsColor", "WrongConstant"})
      private void displayQuestion(ViewHolder holder, int position, ArrayList<QCM>ALLitem){
       QCM current =ALLitem.get(position);





        holder.questiontitle.setText(current.getQuestion().getText().toString());
        holder.Answer1.setText(current.getAns1().getText().toString());
        holder.Answer2.setText(current.getAns2().getText().toString());
        holder.Answer3.setText(current.getAns3().getText().toString());
        holder.Answer4.setText(current.getAns4().getText().toString());

          ArrayList<Answer> correctAnswer = current.getQuestion().getAnswers();



          if(correctAnswer.contains(new Answer(current.getAns1().getText()))) {
               holder.isCorrect1.setTextColor(context.getResources().getColor(R.color.green));
              holder.Answer1.setTextColor(context.getResources().getColor(R.color.green));
          }
          if(correctAnswer.contains(new Answer(current.getAns2().getText()))) {

              holder.isCorrect2.setTextColor(context.getResources().getColor(R.color.green));
              holder.Answer2.setTextColor(context.getResources().getColor(R.color.green));
          }
          if(correctAnswer.contains(new Answer(current.getAns3().getText()))) {
              holder.isCorrect3.setTextColor(context.getResources().getColor(R.color.green));
              holder.Answer3.setTextColor(context.getResources().getColor(R.color.green));
          }
          if(correctAnswer.contains(new Answer(current.getAns4().getText()))) {
              holder.isCorrect4.setTextColor(context.getResources().getColor(R.color.green));
              holder.Answer4.setTextColor(context.getResources().getColor(R.color.green));

          }

    }


}