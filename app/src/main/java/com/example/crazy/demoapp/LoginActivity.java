package com.example.crazy.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    AwesomeValidation validation;
    TextInputLayout layoutEmail, layoutPassword;
    EditText email,password;
    TextView registerLink, forgotPassLink;
    Button login;
    String strEmail, strPassword, localUserEmail, localUserPassword;
    List<UserModel.UserResult> userResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

    }

    public void initViews(){
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPassword = findViewById(R.id.layoutPassword);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.ButtonLogin);
        registerLink = findViewById(R.id.LinkRegister);
        forgotPassLink = findViewById(R.id.LinkForgotPassword);

        validation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        validation.setContext(this);

        registerLink.setOnClickListener(this);
        forgotPassLink.setOnClickListener(this);
        login.setOnClickListener(this);

        validateForm();
    }

    public void loginUser() {

        strEmail = email.getText().toString();
        strPassword = password.getText().toString();

        userResults = (List<UserModel.UserResult>) DbHelper.getRecordsDb(UserModel.UserResult.class);
        // UserModel.UserResult userInfo = new UserModel.UserResult();
        if (userResults.size() > 0) {
            localUserEmail = userResults.get(0).getEmail();
            localUserPassword = userResults.get(0).getPassword();
        }

        if (strEmail.equals(localUserEmail) && strPassword.equals(localUserPassword)) {
            Toast.makeText(this, "Login Credentials Matched Successfully", Toast.LENGTH_SHORT).show();
            //  Snackbar.make(getWindow().getDecorView(),"Login Credentials Matched Successfully",Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email or Password Donot Match", Toast.LENGTH_SHORT).show();
            // Snackbar.make(getWindow().getDecorView(),"Email or Password Donot Match",Snackbar.LENGTH_LONG).show();
        }

    }

    public void validateForm() {
        validation.addValidation(this, R.id.edtEmail, Patterns.EMAIL_ADDRESS, R.string.email_error);
        validation.addValidation(this, R.id.edtPassword, "^[a-zA-Z0-9'@&#.\\s]{6,100}$", R.string.password_length_error);
    }

    private void submitForm() {
        // Validate the form...
        if (validation.validate()) {
            loginUser();
            email.setText("");
            password.setText("");
            // Here, we are sure that form is successfully validated. So, do your stuffs now...
           // Toast.makeText(this, "User LoggedIn Successfully", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LinkRegister:
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

        case R.id.LinkForgotPassword:
            Intent intent1 = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(intent1);
            finish();
            break;

            case R.id.ButtonLogin:
                submitForm();
                break;
         }
    }
}
