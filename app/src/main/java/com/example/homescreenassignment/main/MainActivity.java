package com.example.homescreenassignment.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.homescreenassignment.R;
import com.example.homescreenassignment.feedoperations.CreateFeedActivity;
import com.example.homescreenassignment.feedview.ViewFeedActivity;
import com.example.homescreenassignment.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
    }


    public void createFeed(View createFeed) {
        Intent intent = new Intent(MainActivity.this, CreateFeedActivity.class);
        startActivity(intent);
    }

    public void logout(View logout) {
        FirebaseAuth.getInstance().signOut();
        Intent I = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(I);
    }

    public void viewFeedList(View viewFeeed) {
        Intent I = new Intent(MainActivity.this, ViewFeedActivity.class);
        startActivity(I);
    }
}