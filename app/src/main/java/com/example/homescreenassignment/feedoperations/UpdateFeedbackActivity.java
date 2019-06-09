package com.example.homescreenassignment.feedoperations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homescreenassignment.constants.Constants;
import com.example.homescreenassignment.R;
import com.example.homescreenassignment.datamodel.Feed;
import com.example.homescreenassignment.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;

public class UpdateFeedbackActivity extends AppCompatActivity {

    private EditText updateFeedTextView;
    private FirebaseAuth mAuth;
    private Feed mFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_feedback);
        setUpActivity();
    }

    private void setUpActivity() {
        mAuth = FirebaseAuth.getInstance();
        mFeed = (Feed) getIntent().getSerializableExtra(Constants.UPDATE_FEED_REFERENCE);
        updateFeedTextView = findViewById(R.id.updateFeedView);
        updateFeedTextView.setText(mFeed.getFeed());
    }

    public void updateFeed(View updateFeed) {

        String feedText = updateFeedTextView.getText().toString();

        if (feedText.isEmpty()) {
            updateFeedTextView.setError(getString(R.string.error_empty_feed));
            updateFeedTextView.requestFocus();
            Toast.makeText(UpdateFeedbackActivity.this, getString(R.string.error_empty_feed), Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUtil.update(feedText, mFeed);
            Toast.makeText(UpdateFeedbackActivity.this, getString(R.string.feed_updated_success), Toast.LENGTH_SHORT).show();
        }
    }

}
