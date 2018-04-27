package com.univ_setif.fsciences.qcm.Main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.univ_setif.fsciences.qcm.MainMenu;
import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.Session;

import java.io.File;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private static final String TAG = "Home";
    View v;


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_home, container, false);

        loadUserCard();

        return v;
    }

    public void loadUserCard() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        HashMap<String, String> result = (HashMap<String, String>) sharedPreferences.getAll();
        if (result.size() == 4) {
            TextView userName = v.findViewById(R.id.user_info_fullname);
            userName.setText(result.get("surname") + " " + result.get("firstname"));

            Bitmap profilePicture = loadImage();
            if (profilePicture != null) {
                ImageView userPicture = v.findViewById(R.id.user_info_pic);
                userPicture.setImageBitmap(profilePicture);
            }
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

}
