package com.example.dbmarch11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

public class CoronaNews extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvRSS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_news);

        // Set widget & layout access
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.rvRSS = (RecyclerView) findViewById(R.id.rv_rss);

        // Set a linear layout for our recycler view
        this.rvRSS.setLayoutManager(new LinearLayoutManager(this));

        // Setting the refresh listener
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Call the AsyncTask to fetch the RSS feed for us
                // new FetchFeedTask().execute((Void) null);
            }
        });
    }
}
