package com.example.crazy.demoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mecra on 3/14/2018.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    Context context;
    private List <FeedbackModel.FeedbackResult> feedbackResultArray = new ArrayList<>();

    public FeedbackAdapter(Context context,List <FeedbackModel.FeedbackResult> feedbackResultArray){
        this.context = context;
        this.feedbackResultArray = feedbackResultArray;
    }
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_single_item,parent,false);
        FeedbackAdapter.ViewHolder holder=new FeedbackAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FeedbackAdapter.ViewHolder holder, int position) {
        final FeedbackModel.FeedbackResult pos = feedbackResultArray.get(position);

        if(feedbackResultArray.size() > 0){
            holder.feedbackTitle.setText(pos.getTitle());
            holder.feedbackDes.setText(pos.getFeedback());
        }
    }

    @Override
    public int getItemCount() {
        return feedbackResultArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView feedbackTitle,feedbackDes;

        public ViewHolder(final View itemView) {
            super(itemView);

            feedbackTitle = itemView.findViewById(R.id.txtFeedbackTitle);
            feedbackDes = itemView.findViewById(R.id.txtFeedbackDes);
        }
    }
}
