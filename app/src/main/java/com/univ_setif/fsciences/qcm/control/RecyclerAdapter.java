package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.MCQEditor;
import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzerrad on 31-Mar-18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.QCMViewHolder> {

    private static List<QCM> mQcm = new ArrayList<>();
    private Context mContext;
    private static int position;

    public RecyclerAdapter(Context context, List<QCM> list){
        this.mContext = context;
        mQcm = list;
    }


    @Override
    public QCMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cardview_line, parent, false);
        return new QCMViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(QCMViewHolder holder, int position) {
        this.position = position;

        new Thread(new CardLoader(holder)).start();
    }



    @Override
    public int getItemCount() {
        return mQcm.size();
    }

    static class QCMViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView questionText;
        private TextView answerText1;
        private TextView answerText2;
        private TextView answerText3;
        private TextView answerText4;

        private Context context;


        QCMViewHolder(Context cx, View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            context = cx;

            questionText = itemView.findViewById(R.id.qText);
            answerText1  = itemView.findViewById(R.id.aText1);
            answerText2  = itemView.findViewById(R.id.aText2);
            answerText3  = itemView.findViewById(R.id.aText3);
            answerText4  = itemView.findViewById(R.id.aText4);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context, MCQEditor.class);

            i.putExtra("invoker", "mcqmanager");
            i.putExtra("oldQuestionText", questionText.getText().toString());
            i.putExtra("oldAnswerText1", answerText1.getText().toString());
            i.putExtra("oldAnswerText2", answerText2.getText().toString());
            i.putExtra("oldAnswerText3", answerText3.getText().toString());
            i.putExtra("oldAnswerText4", answerText4.getText().toString());
            i.putExtra("correctAnswer", mQcm.get(position).getQuestion().getAnswer().getText());

            context.startActivity(i);
        }
    }

    class CardLoader implements Runnable{
        QCMViewHolder viewHolder;

        String correctAnswer = mQcm.get(position).getQuestion().getAnswer().getText();

        CardLoader(QCMViewHolder viewHolder){
            this.viewHolder = viewHolder;
        }

        @Override
        public void run() {
            viewHolder.questionText.setText(mQcm.get(position).getQuestion().getText());

            viewHolder.answerText1.setText(mQcm.get(position).getAns1().getText());
            viewHolder.answerText2.setText(mQcm.get(position).getAns2().getText());
            viewHolder.answerText3.setText(mQcm.get(position).getAns3().getText());
            viewHolder.answerText4.setText(mQcm.get(position).getAns4().getText());

            if(viewHolder.answerText1.getText().toString().equals(correctAnswer)){
                viewHolder.answerText1.setTextColor(viewHolder.itemView.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.answerText1.setTypeface(null, Typeface.BOLD);
            }

            else

            if(viewHolder.answerText2.getText().toString().equals(correctAnswer)){
                viewHolder.answerText2.setTextColor(viewHolder.itemView.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.answerText2.setTypeface(null, Typeface.BOLD);
            }

            else

            if(viewHolder.answerText3.getText().toString().equals(correctAnswer)){
                viewHolder.answerText3.setTextColor(viewHolder.itemView.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.answerText3.setTypeface(null, Typeface.BOLD);
            }

            else
            {
                viewHolder.answerText4.setTextColor(viewHolder.itemView.getResources().getColor(R.color.colorPrimaryDark));
                viewHolder.answerText4.setTypeface(null, Typeface.BOLD);
            }
        }
    }
}
