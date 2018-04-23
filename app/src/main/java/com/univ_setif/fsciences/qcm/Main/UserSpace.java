package com.univ_setif.fsciences.qcm.Main;


import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserSpace extends Fragment {
    private TextView mFirstname;
    private TextView mLastname;
    private TextView mSpecialty;
    private TextView mBirthdate;
    private ImageView mProfilePicture;
    private ImageView mDetailsPP;


    private static final String USER_INFO = "userInfo";
    private static final int SELECT_PICTURE = 1;
    private static final int WRITE_EXTERNAL = 10;

    private static final String TAG = "UserSpace";

    public UserSpace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.userspace, container, false);

        CardView information = v.findViewById(R.id.info);
        mFirstname            = v.findViewById(R.id.userCard_firstname);
        mLastname             = v.findViewById(R.id.userCard_lastname);
        mSpecialty           = v.findViewById(R.id.userCard_spec);
        mBirthdate           =v.findViewById(R.id.userCard_birthdate);
        mProfilePicture      =v.findViewById(R.id.userCard_image);

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCardClick(v);
            }
        });

        //populating the card
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        HashMap<String, String> result = (HashMap<String, String>) sharedPreferences.getAll();
        if(result.size() == 4){
            mFirstname.setText(result.get("firstname"));
            mLastname.setText(result.get("surname"));
            mSpecialty.setText(result.get("specialty"));
            mBirthdate.setText(result.get("birthdate"));
            Bitmap profilePicture = loadImage();
            if(profilePicture != null)
                mProfilePicture.setImageBitmap(profilePicture);
        }


        return v;
    }
    public void OnCardClick(View v) {
          /*========================================
            I N F L A T I N G       V I E W
         =========================================*/
        //Creating Builder
        android.support.v7.app.AlertDialog.Builder mUserEdit = new android.support.v7.app.AlertDialog.Builder(getActivity());

        //inflating layout on view
        @SuppressLint("InflateParams")
        final View editorView = getLayoutInflater().inflate(R.layout.dialog_user_details_edit, null);
        mUserEdit.setView(editorView);

        //Creating Dialog Popup
        final android.support.v7.app.AlertDialog userEditor = mUserEdit.create();
        userEditor.setCancelable(false);
        userEditor.setCanceledOnTouchOutside(true);

        //retrieving EditTexts
        final EditText lastname = editorView.findViewById(R.id.user_lastname);
        lastname.setText(mLastname.getText());
        final EditText firstname = editorView.findViewById(R.id.user_firstname);
        firstname.setText(mFirstname.getText());
        final EditText specialty = editorView.findViewById(R.id.user_spec);
        specialty.setText(mSpecialty.getText());

        mDetailsPP = editorView.findViewById(R.id.user_image);
        Bitmap img = loadImage();
        if(img != null) mDetailsPP.setImageBitmap(img);


        final Button update = editorView.findViewById(R.id.user_update);



        /*======================================
               D A T E        P I C K E R
         =======================================*/

        final TextView birthdate = editorView.findViewById(R.id.user_birthdate);
        birthdate.setText(mBirthdate.getText());
        final DatePickerDialog.OnDateSetListener onDateSetListener;

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                birthdate.setText(date);
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        onDateSetListener,
                        year, month, day
                );

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.updateDate(1995, 0, 1);
                datePickerDialog.show();
            }
        });





        /*=======================================
              I M A G E     U P L O A D E R
         ========================================*/


        final Button imageUpload = editorView.findViewById(R.id.user_image_upload);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galery, SELECT_PICTURE);
            }
        });



        /*===========================================
            O N    U P D A T E    C L I C K
         ===========================================*/



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Surname cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (firstname.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Firstname cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (specialty.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "specialty cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }



                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("surname", lastname.getText().toString());
                editor.putString("firstname", firstname.getText().toString());
                editor.putString("specialty", specialty.getText().toString());
                editor.putString("birthdate", birthdate.getText().toString());
                editor.apply();

                Bitmap profilePicture = ((BitmapDrawable) mDetailsPP.getDrawable()).getBitmap();
               storeImage(profilePicture);

                mLastname.setText(lastname.getText().toString());
                mFirstname.setText(firstname.getText().toString());
                mSpecialty.setText(specialty.getText());
                mBirthdate.setText(birthdate.getText());

                userEditor.cancel();
            }
        });


        userEditor.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == SELECT_PICTURE){
            Uri imageUri = data.getData();
            mDetailsPP.setImageURI(imageUri);
            mProfilePicture.setImageURI(imageUri);
        }
    }
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();

        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
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
    private  File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getPackageName()
                + "/img");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL);
                }else{
                    return null;
                }
            }
        }

        File mediaFile;
        String profile="profile.jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + profile);
        return mediaFile;
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
