package com.example.homescreenassignment.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.homescreenassignment.constants.Constants;
import com.example.homescreenassignment.feedoperations.UpdateFeedbackActivity;
import com.example.homescreenassignment.feedview.FeedListAdapter;
import com.example.homescreenassignment.datamodel.Feed;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtil {

    static public void deleteFeed(final Feed feedItem) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FEED);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                    Feed feed = feedSnapshot.getValue(Feed.class);
                    if ((feed.time.equals(feedItem.time))) {
                        feedSnapshot.getRef().removeValue();
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    static public void goToUpdateFeed(Context mContext, final Feed feedItem) {
        Intent intent = new Intent(mContext, UpdateFeedbackActivity.class);
        intent.putExtra(Constants.UPDATE_FEED_REFERENCE, feedItem);
        mContext.startActivity(intent);
    }

    static public void setVisibility(FeedListAdapter.MyViewHolder myViewHolder, final Feed feedItem, String uId) {
        if (!uId.equals(feedItem.feedUserId)) {
            myViewHolder.editFeed.setVisibility(View.INVISIBLE);
            myViewHolder.deleteFeed.setVisibility(View.INVISIBLE);
        } else {
            myViewHolder.editFeed.setVisibility(View.VISIBLE);
            myViewHolder.deleteFeed.setVisibility(View.VISIBLE);
        }
    }

    static public void update(final String feedText, final Feed mFeed) {

        DatabaseReference ref = FirebaseUtil.getFeedReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                    Feed feed = feedSnapshot.getValue(Feed.class);
                    if (feed.getTime().equals(mFeed.getTime())) {
                        mFeed.setFeed(feedText);
                        Long tsLong = System.currentTimeMillis() / 1000;
                        mFeed.setTime(tsLong);
                        feedSnapshot.getRef().setValue(mFeed);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    static public void createFeed(String feedText) {

        final FirebaseUser currentUser = getFirebaseAuth().getCurrentUser();
        DatabaseReference mDatabase = FirebaseUtil.getFeedReference();
        String newKey = mDatabase.push().getKey();

        Long tsLong = System.currentTimeMillis() / 1000;

        Feed feed = new Feed(currentUser.getUid(), feedText, tsLong);
        mDatabase.child(newKey).setValue(feed);
    }

    static public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    static public DatabaseReference getFeedReference() {
        return FirebaseDatabase.getInstance().getReference(Constants.FEED);
    }


}
