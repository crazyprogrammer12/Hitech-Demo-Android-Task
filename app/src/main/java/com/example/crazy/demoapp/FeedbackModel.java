package com.example.crazy.demoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by mecra on 3/14/2018.
 */

public class FeedbackModel {

    List<FeedbackResult> feedbackResults;

    public List<FeedbackResult> getFeedbackResults() {
        return feedbackResults;
    }

    public void setFeedbackResults(List<FeedbackResult> feedbackResults) {
        this.feedbackResults = feedbackResults;
    }

    @Table(name = "FeedbackResult")
    public static class FeedbackResult extends Model {

        @Column(name = "Title")
        private String title;

        @Column(name = "Feedback")
        private String feedback;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }
}
