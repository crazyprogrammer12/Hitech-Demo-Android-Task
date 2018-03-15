package com.example.crazy.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtname,edtemail,edtmobile,edtcompany,edtpassword,edtConfirmPassword;
    Button btnregister;
    TextInputLayout layoutName,layoutEmail,layoutMobile,layoutCompany,layoutPassword,layoutConfirmPassword;
    List<UserModel.UserResult> userResultList;
    List<UserModel.UserResult> localList;
    AwesomeValidation validation;
    String strName,strEmail,strMobile,strCompany,strPassword,strConfirmPassword,localEmail;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    public void initViews(){
        layoutName = findViewById(R.id.LayoutName);
        layoutEmail = findViewById(R.id.LayoutEmail);
        layoutPassword = findViewById(R.id.LayoutPassword);
        layoutCompany = findViewById(R.id.LayoutCompany);
        layoutMobile = findViewById(R.id.LayoutMobile);
        layoutConfirmPassword = findViewById(R.id.LayoutConfirmPassword);

        edtname = findViewById(R.id.edtName);
        edtemail = findViewById(R.id.edtEmail);
        edtcompany = findViewById(R.id.edtCompany);
        edtmobile = findViewById(R.id.edtMobile);
        edtpassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnregister = findViewById(R.id.ButtonRegister);

        validation=new AwesomeValidation(ValidationStyle.UNDERLABEL);
        validation.setContext(this);
        btnregister.setOnClickListener(this);

        validateForm();
    }

    public void registerUser(){
        strEmail = edtemail.getText().toString();
        Log.d(TAG, "registerUser: Email "+strEmail);
        strCompany = edtcompany.getText().toString();
        strMobile = edtmobile.getText().toString();
        strName = edtname.getText().toString();
        strPassword = edtpassword.getText().toString();
        strConfirmPassword = edtConfirmPassword.getText().toString();

        userResultList = new ArrayList<>();
        UserModel.UserResult saveInfo = new UserModel.UserResult();

        saveInfo.setEmail(strEmail);
        saveInfo.setMobile(strMobile);
        saveInfo.setName(strName);
        saveInfo.setCompany(strCompany);
        saveInfo.setPassword(strPassword);
        userResultList.add(saveInfo);

        if (userResultList.size() > 0) {
            DbHelper.deleteTable(UserModel.UserResult.class);
            ActiveAndroid.beginTransaction();
            try {
                for (UserModel.UserResult b : userResultList) {
                    b.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }

       // Snackbar.make(getWindow().getDecorView(),"User Registered Successfully",Snackbar.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_LONG).show();

    }

    public void submitForm(){
        if (validation.validate()) {
            registerUser();
            validation.clear();
            edtname.setText("");
            edtpassword.setText("");
            edtcompany.setText("");
            edtmobile.setText("");
            edtemail.setText("");
            edtConfirmPassword.setText("");

        }
    }

    public void validateForm()
    {
        validation.addValidation(this,R.id.edtName,RegexTemplate.NOT_EMPTY,R.string.name_error);
        validation.addValidation(this,R.id.edtEmail, Patterns.EMAIL_ADDRESS,R.string.email_error);
        validation.addValidation(this,R.id.edtMobile,"[0-9]{10}",R.string.mobile_length_error);
        validation.addValidation(this,R.id.edtCompany,RegexTemplate.NOT_EMPTY,R.string.company_error);
        validation.addValidation(this,R.id.edtPassword,"^[a-zA-Z0-9'@&#.\\s]{6,100}$",R.string.password_length_error);
        validation.addValidation(this,R.id.edtConfirmPassword,RegexTemplate.NOT_EMPTY,R.string.error_password_match);
        validation.addValidation(this,R.id.edtConfirmPassword,R.id.edtPassword,R.string.error_password_match);
    }

    public void checkEmail(){
        localList = (List<UserModel.UserResult>) DbHelper.getRecordsDb(UserModel.UserResult.class);
        if(localList.size() > 0) {
            localEmail = localList.get(0).getEmail();
        }

        if(strEmail.equals(localEmail)){
            Toast.makeText(this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonRegister:
                submitForm();
                break;
        }
    }
}
