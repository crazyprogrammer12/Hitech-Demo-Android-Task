package com.example.crazy.demoapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    AwesomeValidation validation;
    EditText newPass,confirmNewPass;
    TextInputLayout layoutNewPass,layoutConfirmNewPass;
    String strNewPass,strConfirmNewPass,strName,strCompany,strMobile,strEmail;
    Button btnReset;
    TextView linkLogin;
    List<UserModel.UserResult> userResults,localList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();

    }

    public void initViews(){
        newPass = findViewById(R.id.edtNewPassword);
        confirmNewPass = findViewById(R.id.edtConfirmNewPassword);
        layoutNewPass = findViewById(R.id.LayoutNewPassword);
        layoutConfirmNewPass = findViewById(R.id.LayoutConfirmNewPassword);
        btnReset = findViewById(R.id.ButtonReset);
        linkLogin = findViewById(R.id.LinkLogin);

        validation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        validation.setContext(getApplicationContext());

        linkLogin.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        validateForm();
    }

    public void resetPassword(){
        strNewPass = newPass.getText().toString();
        strConfirmNewPass = confirmNewPass.getText().toString();

        userResults = new ArrayList<>();
        UserModel.UserResult savePass = new UserModel.UserResult();

        localList = (List<UserModel.UserResult>) DbHelper.getRecordsDb(UserModel.UserResult.class);

        strName = localList.get(0).getName();
        strEmail = localList.get(0).getEmail();
        strMobile = localList.get(0).getMobile();
        strConfirmNewPass = localList.get(0).getCompany();

        savePass.setName(strName);
        savePass.setEmail(strEmail);
        savePass.setCompany(strCompany);
        savePass.setMobile(strMobile);
        savePass.setPassword(strNewPass);
        userResults.add(savePass);

        if(userResults.size() > 0){
            DbHelper.deleteTable(UserModel.UserResult.class);
            ActiveAndroid.beginTransaction();
            try {
                for (UserModel.UserResult b : userResults) {
                    b.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
        Toast.makeText(this, "Password Reset Successfully..", Toast.LENGTH_SHORT).show();

        /*if(strNewPass.equals(strConfirmNewPass)){
            Toast.makeText(this, "Password Reset Successfully..", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void validateForm(){
        validation.addValidation(this,R.id.edtNewPassword,"^[a-zA-Z0-9'@&#.\\s]{6,100}$",R.string.password_length_error);
        validation.addValidation(this,R.id.edtConfirmNewPassword,R.id.edtNewPassword,R.string.error_password_match);
    }

    public void submitForm(){
        if(validation.validate()){
            resetPassword();
            validation.clear();
            newPass.setText("");
            confirmNewPass.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LinkLogin:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.ButtonReset:
                submitForm();
                break;
        }
    }
}
