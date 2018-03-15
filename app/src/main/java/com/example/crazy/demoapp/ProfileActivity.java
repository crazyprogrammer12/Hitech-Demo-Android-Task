package com.example.crazy.demoapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.facebook.CallbackManager;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.widget.LikeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    EditText name,mobile,email,company;
    List<UserModel.UserResult> userResults = new ArrayList<>();
    List<UserModel.UserResult> localList;
    LikeView likeView;
    Button update,save;
    CallbackManager callbackManager;
    String strName,strEmail,strCompany,strMobile,strPassword;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = findViewById(R.id.txtFullName);
        mobile =  findViewById(R.id.txtMobile);
        email = findViewById(R.id.txtMail);
        company = findViewById(R.id.txtCompany);
        likeView = findViewById(R.id.facebookLike);
        update = findViewById(R.id.btnUpdate);
        save = findViewById(R.id.btnSave);

        callbackManager = CallbackManager.Factory.create();
        setLikeButton();
        userResults = (List<UserModel.UserResult>) DbHelper.getRecordsDb(UserModel.UserResult.class);
        if(userResults.size() > 0){
            name.setText(userResults.get(0).getName());
            mobile.setText(userResults.get(0).getMobile());
            email.setText(userResults.get(0).getEmail());
            company.setText(userResults.get(0).getCompany());
        } else {
            name.setText("N/A");
            mobile.setText("N/A");
            email.setText("N/A");
            company.setText("N/A");
            Toast.makeText(ProfileActivity.this, "Please Register First to See Profile", Toast.LENGTH_SHORT).show();
           // Snackbar.make(getWindow().getDecorView(),"Please Register First to See Profile",Snackbar.LENGTH_LONG).show();
        }

        update.setVisibility(View.VISIBLE);
        save.setVisibility(View.GONE);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                email.setEnabled(true);
                company.setEnabled(true);
                mobile.setEnabled(true);
                update.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localList = new ArrayList<>();
                UserModel.UserResult saveNewInfo = new UserModel.UserResult();

                strName = name.getText().toString();
                strEmail = email.getText().toString();
                strCompany = company.getText().toString();
                strMobile = mobile.getText().toString();
                strPassword = userResults.get(0).getPassword();

                saveNewInfo.setName(strName);
                saveNewInfo.setEmail(strEmail);
                saveNewInfo.setMobile(strMobile);
                saveNewInfo.setCompany(strCompany);
                saveNewInfo.setPassword(strPassword);
                localList.add(saveNewInfo);

                if (localList.size() > 0) {
                    DbHelper.deleteTable(UserModel.UserResult.class);
                    ActiveAndroid.beginTransaction();
                    try {
                        for (UserModel.UserResult b : localList) {
                            b.save();
                        }
                        ActiveAndroid.setTransactionSuccessful();
                    } finally {
                        ActiveAndroid.endTransaction();
                    }
                }

                name.setEnabled(false);
                email.setEnabled(false);
                company.setEnabled(false);
                mobile.setEnabled(false);

                Toast.makeText(ProfileActivity.this, "Profile Updated and Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void setLikeButton() {
        likeView = findViewById(R.id.facebookLike);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);

        String pageUrlToLike = "https://www.facebook.com/Thecoffeecoders-418200285040181/";
        likeView.setObjectIdAndType(pageUrlToLike, LikeView.ObjectType.PAGE);
    }
}
