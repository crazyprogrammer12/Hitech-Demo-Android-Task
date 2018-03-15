package com.example.crazy.demoapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FeedbackAdapter feedbackAdapter;
   // TextView feedbackTitle,feedbackDes;
    List<FeedbackModel.FeedbackResult> feedbackResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

      //  feedbackTitle = findViewById(R.id.txtFeedbackTitle);
      //  feedbackDes = findViewById(R.id.txtFeedbackDes);
        recyclerView = findViewById(R.id.feedback_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        feedbackResultList = (List<FeedbackModel.FeedbackResult>) DbHelper.getRecordsDb(FeedbackModel.FeedbackResult.class);
        /*if(feedbackResultList.size() > 0){
            feedbackTitle.setText(feedbackResultList.get(0).getTitle());
            feedbackDes.setText(feedbackResultList.get(0).getTitle());
        } else {
            feedbackTitle.setText("N/A");
            feedbackDes.setText("N/A");
            Snackbar.make(getWindow().getDecorView(),"Please Submit Feedback First to See",Snackbar.LENGTH_LONG).show();
        }*/
        feedbackAdapter = new FeedbackAdapter(getApplicationContext(),feedbackResultList);
        recyclerView.setAdapter(feedbackAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
