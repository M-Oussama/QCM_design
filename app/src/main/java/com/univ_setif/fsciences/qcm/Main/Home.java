package com.univ_setif.fsciences.qcm.Main;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.control.AnswerCTRL;
import com.univ_setif.fsciences.qcm.control.UserLogCTRL;
import com.univ_setif.fsciences.qcm.entity.UserLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private static final String TAG = "Home";
    View v;
    public static  ImageView Settingsicon,helpicon;

    public Home() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_home, container, false);
        Settingsicon = v.findViewById(R.id.settings_icon);
        helpicon = v.findViewById(R.id.help_icon);


        new loadUser().run();

        return v;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(v != null && isVisibleToUser)
            new loadUser().run();
    }


    private class loadUser implements Runnable{

        private void loadUserCard() {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            HashMap<String, String> result = (HashMap<String, String>) sharedPreferences.getAll();
            if (result.size() == 4) {
                TextView userName = v.findViewById(R.id.user_info_fullname);
                TextView specialty = v.findViewById(R.id.specialty);
                ImageView userImage = v.findViewById(R.id.user_image_pic);

                userName.setText(result.get("surname") + " " + result.get("firstname"));
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(userName, 18, 30, 1, TypedValue.COMPLEX_UNIT_DIP);
                specialty.setText(result.get("specialty"));
                userImage.setImageBitmap(loadImage());
            }

                TextView average = v.findViewById(R.id.average);
                ArrayList<UserLog> userLogs = null;
                try {
                    userLogs = new UserLogCTRL(getContext(), false).getLog();
                } catch (IOException e) {
                    Log.w("Home", "Could not read from log file");
                }

                if(userLogs != null && userLogs.size() != 0){
                    double avg = 0;

                    for (UserLog logline: userLogs)
                        avg += logline.getNote();

                    avg /= userLogs.size();

                    avg = AnswerCTRL.round(avg, 2);
                    String avgStr = avg + "/20";
                    average.setText(avgStr);
                }else{
                    average.setText("0.0/20");
                }


        }

        private Bitmap loadImage(){
            File pictureFile = getInputMediaFile();

            if (pictureFile == null) {
                Log.d(TAG,
                        "Error creating media file, check storage permissions: ");// e.getMessage());
                return null;
            }

            try {
                return BitmapFactory.decodeFile(pictureFile.getPath());
            } catch (Exception e) {
                Log.w(TAG, "Could not decode file: " + pictureFile.getPath());
                return null;
            }
        }
        private  File getInputMediaFile(){
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                    + "/Android/data/"
                    + getActivity().getPackageName()
                    + "/img");

            if (! mediaStorageDir.exists())
                return null;

            File mediaFile;
            String profile="profile.jpg";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + profile);
            return mediaFile;
        }

        @Override
        public void run() {
            loadUserCard();
        }
    }
}
