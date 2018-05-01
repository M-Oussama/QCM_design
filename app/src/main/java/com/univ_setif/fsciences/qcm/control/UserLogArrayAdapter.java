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
import com.univ_setif.fsciences.qcm.entity.UserLog;

import java.util.List;

public class UserLogArrayAdapter extends ArrayAdapter<UserLog> {

    private Context context;
    private List<UserLog> userLogs;

    public UserLogArrayAdapter(@NonNull Context context, int resource, @NonNull List<UserLog> objects) {
        super(context, resource, objects);

        this.context = context;
        userLogs     = objects;
    }

    @Override
    public int getCount() {
        return userLogs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View userLogItem = inflater.inflate(R.layout.listview_logline, parent, false);

        UserLog item = userLogs.get(position);

        TextView logDate    = userLogItem.findViewById(R.id.log_date);
        TextView logNbrQCM  = userLogItem.findViewById(R.id.log_nbrQCM);
        TextView logNote    = userLogItem.findViewById(R.id.log_note);
        TextView logElapsed = userLogItem.findViewById(R.id.log_elapsed);

        logDate.setText(item.getDate());
        logNote.setText(Double.toString(item.getNote()));
        logNbrQCM.setText(Integer.toString(item.getNbrQCM()));
        logElapsed.setText(item.getElapsedTime());

        return userLogItem;
    }
}
