package com.example.homescreenassignment.feedoperations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.homescreenassignment.R;
import com.example.homescreenassignment.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;

public class CreateFeedActivity extends AppCompatActivity {
    public EditText feedTextView;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_feed_activity);
        setUpActivity();
    }

    private void setUpActivity() {
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        feedTextView = findViewById(R.id.feedText);
    }

    public void createFeedViewClickHandler(View createFeed) {

        String feedText = feedTextView.getText().toString();

        if (feedText.isEmpty()) {
            feedTextView.setError(getResources().getString(R.string.error_empty_feed));
            feedTextView.requestFocus();
            Toast.makeText(CreateFeedActivity.this, getString(R.string.error_empty_feed), Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUtil.createFeed(feedText);
            Toast.makeText(CreateFeedActivity.this,  getString(R.string.feed_creation_success), Toast.LENGTH_SHORT).show();
        }
    }


}
