package com.example.homescreenassignment.util;

import com.google.firebase.auth.FirebaseUser;

public class LoginUtil {

    static public boolean isLoggedIn() {
        FirebaseUser currentUser = FirebaseUtil.getFirebaseAuth().getCurrentUser();
        if (currentUser != null) {
            return true;
        } else {
            return false;
        }
    }

    static public String getLoggedInUserId() {
        FirebaseUser currentUser = FirebaseUtil.getFirebaseAuth().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return "";
        }
    }

}
