package com.example.homescreenassignment.feedview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.homescreenassignment.R;
import com.example.homescreenassignment.datamodel.Feed;
import com.example.homescreenassignment.util.FirebaseUtil;
import com.example.homescreenassignment.util.LoginUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewFeedActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private FeedListAdapter mFeedListAdapter;
    private List<Feed> mFeedList;
    private SwipeRefreshLayout swipeContainer;

    public static final int PAGE_SIZE = 10;
    public int pageNumber = 1;

    private boolean loading = true;
    private String lastSeenKey;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    private DatabaseReference mDatabaseReference;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_feed);
        setUpActivity();
    }

    private void setUpActivity() {

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeContainer.setRefreshing(true);
        mRecyclerView = findViewById(R.id.feedListView);
        mLayoutManager = new LinearLayoutManager(this);
        mFeedList = new ArrayList<Feed>();
        mDatabaseReference = FirebaseUtil.getFeedReference();
        mQuery = mDatabaseReference.limitToFirst(10);
        setUpFeedListView(LoginUtil.getLoggedInUserId());

        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatabaseReference = FirebaseUtil.getFeedReference();
                mQuery = mDatabaseReference.limitToFirst(10);
                setUpFeedListView(LoginUtil.getLoggedInUserId());
                pageNumber = 1;
                loading = true;
            }
        });

    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        ++pageNumber;
                        loading = false;
                        mQuery = mDatabaseReference.orderByKey().startAt(lastSeenKey).limitToFirst(PAGE_SIZE * pageNumber);
                        setUpFeedListView(LoginUtil.getLoggedInUserId());
                    }
                }
            }
        }

    };

    public void setUpFeedListView(final String Uid) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {
                mFeedList.clear();
                for (DataSnapshot feedSnapshot : dataSnapshots.getChildren()) {
                    Feed feed = feedSnapshot.getValue(Feed.class);
                    mFeedList.add(feed);
                    lastSeenKey = feedSnapshot.getKey();
                }

                if (mFeedList.size() > 0) {

                    mRecyclerView.setLayoutManager(mLayoutManager);

                    mFeedListAdapter = new FeedListAdapter(ViewFeedActivity.this, mFeedList, mDatabaseReference, Uid);
                    mRecyclerView.setAdapter(mFeedListAdapter);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mQuery.addValueEventListener(postListener);
    }
}
