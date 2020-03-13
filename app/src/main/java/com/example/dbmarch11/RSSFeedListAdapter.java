package com.example.dbmarch11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RSSFeedListAdapter
        extends RecyclerView.Adapter<RSSFeedListAdapter.FeedModelViewHolder> {

    private ArrayList<RSSFeedModel> RSSFeedModelList;

    public static class FeedModelViewHolder extends RecyclerView.ViewHolder {
        private View RSSFeedView;

        public FeedModelViewHolder(View v) {
            super(v);
            this.RSSFeedView = v;
        }
    }


    public RSSFeedListAdapter(ArrayList<RSSFeedModel> rssFeedModels) {
        this.RSSFeedModelList = rssFeedModels;
    }


    @Override
    public FeedModelViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rss_feed_item, parent, false);
        FeedModelViewHolder holder = new FeedModelViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedModelViewHolder holder, int position) {
        RSSFeedModel rssFeedModel = RSSFeedModelList.get(position);

        // Set the title of the article
        //((TextView)holder.RSSFeedView.findViewById(R.id.tv_title)).setText(rssFeedModel.title);

        // Get the LinearLayout that holds the entire rss_feed_item
        LinearLayout parent = (LinearLayout) ((ViewGroup)holder.RSSFeedView).getChildAt(0);
        int numChildren = parent.getChildCount();

        TextView tv = (TextView) parent.getChildAt(0);
        tv.setText(rssFeedModel.title);
        // Set the button that will go to an article on the actual CBC news site
        //((Button)holder.RSSFeedView.findViewById(R.id.btn_gotosite)).setText(rssFeedModel.link);

        //((Button)((ViewGroup)holder.RSSFeedView).getChildAt(2)).setText(rssFeedModel.link);

        Button btn = (Button) parent.getChildAt(1);
        btn.setText(rssFeedModel.link);


    }

    @Override
    public int getItemCount() {
        return this.RSSFeedModelList.size();
    }
}
