package com.example.libraapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private TextView user_name;
    private TextView user_email;
    private Button mSignOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user_name = findViewById(R.id.text_user_name);
        user_email = findViewById(R.id.text_email);
        mSignOutBtn = findViewById(R.id.btn_sign_out);

       FirebaseUser firebaseUser =  FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String name  = firebaseUser.getDisplayName();
            String emailID = firebaseUser.getEmail();
            user_name.setText(name);
            user_email.setText(emailID);
        }
        mSignOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finishAffinity();
        });

    }
}
