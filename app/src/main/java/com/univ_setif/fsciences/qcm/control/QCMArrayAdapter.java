package com.univ_setif.fsciences.qcm.control;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.entity.QCM;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hzerrad on 24-Mar-18.
 */

public class QCMArrayAdapter extends ArrayAdapter<QCM> {
    private Context mContext;
    private List<QCM> mQCM;

    public QCMArrayAdapter(@NonNull Context context, int resource, @NonNull List<QCM> objects) {
        super(context, resource, objects);
        mContext = context;
        mQCM = objects;
    }

    @Override
    public int getCount() {
        if(mQCM != null)
            return mQCM.size();
        else
            return -1;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItem = inflater.inflate(R.layout.listview_line, parent, false);

        TextView questionText = listItem.findViewById(R.id.questionText);
        TextView answerText1  = listItem.findViewById(R.id.answerText1);
        TextView answerText2  = listItem.findViewById(R.id.answerText2);
        TextView answerText3  = listItem.findViewById(R.id.answerText3);
        TextView answerText4  = listItem.findViewById(R.id.answerText4);
    if(mQCM.get(position) != null) {
        questionText.setText(mQCM.get(position).getQuestion().getText());
        answerText1.setText(mQCM.get(position).getAns1().getText());
        answerText2.setText(mQCM.get(position).getAns2().getText());
        answerText3.setText(mQCM.get(position).getAns3().getText());
        answerText4.setText(mQCM.get(position).getAns4().getText());
    }

        return listItem;
    }
}
