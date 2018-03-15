package com.example.crazy.demoapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    EditText feedbackDes;
    Button submit;
    Spinner titles;
    AwesomeValidation validation;
    List<FeedbackModel.FeedbackResult> feedbackResults;
    String spinnerItem,strFeedback;
    private static final String TAG = "FeedbackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

       initViews();
    }

    public void initViews(){
        feedbackDes = findViewById(R.id.edtFeedback);
        submit = findViewById(R.id.btnSubmit);
        titles = findViewById(R.id.feedbackTitle);

        validation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        validation.setContext(this);
        submit.setOnClickListener(this);


        titles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerItem = titles.getSelectedItem().toString();
                //  Toast.makeText(FeedbackActivity.this, "Spinner Item " + spinnerItem, Toast.LENGTH_SHORT).show();
                // Log.d(TAG, "onItemSelected: Spinner iTEM"+spinnerItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        validateForm();
    }

    public void sendFeedback()
    {
        strFeedback = feedbackDes.getText().toString();

        feedbackResults = new ArrayList<>();
        FeedbackModel.FeedbackResult feedbackInfo = new FeedbackModel.FeedbackResult();

        feedbackInfo.setTitle(spinnerItem);
      //  Toast.makeText(this, "New Spinner Item " +spinnerItem, Toast.LENGTH_SHORT).show();
        feedbackInfo.setFeedback(strFeedback);
        feedbackResults.add(feedbackInfo);

        if(feedbackResults.size() > 0){
         //   DbHelper.deleteTable(FeedbackModel.FeedbackResult.class);
            ActiveAndroid.beginTransaction();
            try{
                for(FeedbackModel.FeedbackResult b:feedbackResults){
                    b.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }finally {
                ActiveAndroid.endTransaction();
            }

            Toast.makeText(FeedbackActivity.this, "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
            // Snackbar.make(getWindow().getDecorView(),"Feedback Submitted Successfully",Snackbar.LENGTH_LONG).show();
        } else {
            Toast.makeText(FeedbackActivity.this, "No Feedback Exists..", Toast.LENGTH_SHORT).show();
        }

    }

    public void validateForm()
    {
        validation.addValidation(this,R.id.edtFeedback, RegexTemplate.NOT_EMPTY,R.string.feedback_error);
    }

    public void submitForm(){
        if(validation.validate()){
            sendFeedback();
            feedbackDes.setText("");
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                submitForm();
                break;
        }
    }
}
