package com.example.homescreenassignment.feedview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.example.homescreenassignment.R;
import com.example.homescreenassignment.datamodel.Feed;
import com.example.homescreenassignment.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Feed> mFeedList;
    DatabaseReference ref;
    private String UId;
    public FeedListAdapter(Context mContext, List<Feed> feedList, DatabaseReference mRef, String uid) {
        this.mContext = mContext;
        this.mFeedList = feedList;
        this.ref = mRef;
        this.UId = uid;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_feed_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Feed feedItem = mFeedList.get(i);
        myViewHolder.feedUserId.setText(feedItem.getFeedUserId());
        myViewHolder.feedText.setText(feedItem.getFeed());
        FirebaseUtil.setVisibility(myViewHolder, feedItem, this.UId);
        setClickListeners(myViewHolder, feedItem);
    }

    private void setClickListeners(FeedListAdapter.MyViewHolder myViewHolder, final Feed feedItem) {

        myViewHolder.deleteFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.deleteFeed(feedItem);
            }
        });

        myViewHolder.editFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUtil.goToUpdateFeed(mContext, feedItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView feedUserId, feedText;
        public ImageView editFeed, deleteFeed;
        public MyViewHolder(View view) {
            super(view);
            feedUserId =  view.findViewById(R.id.feedUserId);
            feedText = view.findViewById(R.id.feedText);
            editFeed = view.findViewById(R.id.editFeed);
            deleteFeed = view.findViewById(R.id.deleteFeed);
        }
    }

}
