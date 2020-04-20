package com.mobiledev183.lawapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobiledev183.lawapp.Model.LoginModel;
import com.mobiledev183.lawapp.ProgressDialogData;
import com.mobiledev183.lawapp.R;

public class LoginActivity extends AppCompatActivity {

    private TextView mEmail;
    private TextView mPassword;
    private Button mBtnLogin;
    private FirebaseAuth mFireBaseAuth;
    private TextView navigationToRegisterPageText;
    private TextView mForgotpasswordText;
    private ProgressDialogData progressDialogData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login_page);
        progressDialogData = new ProgressDialogData(this);
        mFireBaseAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.edit_emailid);
        mPassword = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.buttonLogin);
        mForgotpasswordText = findViewById(R.id.text_forgotpassword);
        navigationToRegisterPageText = findViewById(R.id.text_nagation_to_register_activity);

        navigationToRegisterPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            if(mFireBaseAuth.getCurrentUser() != null){
                Intent dashBoardIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(dashBoardIntent);
                finish();
            }
        }

        /**
         *  Handle login functionality using firebase..
         */
        mBtnLogin.setOnClickListener(view -> {
            LoginModel login = new LoginModel();
            login.setEmail(mEmail.getText().toString());
            login.setPassword(mPassword.getText().toString());
            if (login.getEmail().isEmpty()) {
                mEmail.setError("Please Enter Email");
                mEmail.requestFocus();
            } else if (login.getPassword().isEmpty()) {
                mPassword.setError("Please Enter Password");
                mPassword.requestFocus();
            } else {
                progressDialogData.show();
                mFireBaseAuth.signInWithEmailAndPassword(login.getEmail(), login.getPassword()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Please enter correct Email id and Password", Toast.LENGTH_SHORT).show();
                            progressDialogData.dismiss();
                        } else {
                            Intent dashBoardIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(dashBoardIntent);
                            progressDialogData.dismiss();
                            finish();

                        }
                    }
                });
            }
        });

        /**
         *  navigate to Forgot Password activity..
         */
        mForgotpasswordText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}