package com.univ_setif.fsciences.qcm.Main;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.univ_setif.fsciences.qcm.R;
import com.univ_setif.fsciences.qcm.control.LoginCTRL;


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
        final View v =inflater.inflate(R.layout.adminspace, container, false);

        Button submit = v.findViewById(R.id.sign_in);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout password = v.findViewById(R.id.password);
                final String pass = password.getEditText().getText().toString();
                LoginCTRL.verify(view.getContext(), pass);
            }
        });

        return  v;
    }


}


