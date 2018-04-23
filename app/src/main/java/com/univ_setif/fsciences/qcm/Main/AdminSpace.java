package com.univ_setif.fsciences.qcm.Main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.univ_setif.fsciences.qcm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSpace extends Fragment {

   Button login;

    public AdminSpace() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.adminspace, container, false);



        return  view;
    }



    }


