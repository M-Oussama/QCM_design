package com.univ_setif.fsciences.qcm.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.univ_setif.fsciences.qcm.R;

public class AdminSpaceFragment extends Fragment {


    public AdminSpaceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_space, container, false);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



}
