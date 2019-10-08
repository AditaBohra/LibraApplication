package com.example.libraapplication;

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

public class MainActivity extends AppCompatActivity {

    private TextView mEmail;
    private TextView mPassword;
    private Button mBtnLogin;
    private FirebaseAuth mFireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFireBaseAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.edit_emailid);
        mPassword = findViewById(R.id.edit_password);
        mBtnLogin = findViewById(R.id.buttonLogin);

        mFireBaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Toast.makeText(MainActivity.this,"User Authorized",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = mEmail.getText().toString();
                String passTxt = mPassword.getText().toString();
                if(emailTxt.isEmpty()){
                    mEmail.setError("Please Enter Email");
                    mEmail.requestFocus();
                }
                else if(passTxt.isEmpty()){
                    mPassword.setError("Please Enter Password");
                    mPassword.requestFocus();
                }
                else {
                    mFireBaseAuth.signInWithEmailAndPassword(emailTxt,passTxt).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Auth Failed",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}
