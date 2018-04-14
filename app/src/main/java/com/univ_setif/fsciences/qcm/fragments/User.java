package com.univ_setif.fsciences.qcm.fragments;

/**
 * Singleton
 * Created by hzerrad on 21-Mar-18.
 */

public class User {
    private static User user = null;

    private User(){

    }

    public static User getUser() {
        if(user == null){
          user = new User();
        }

        return user;
    }

}
