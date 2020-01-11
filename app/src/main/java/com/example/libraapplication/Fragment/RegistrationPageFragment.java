package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraapplication.Activity.LoginActivity;
import com.example.libraapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegistrationPageFragment extends Fragment {

    private TextView loginNavigatePageText;
    private TextView mEmailTextView;
    private TextView mPassTextView;
    private FirebaseAuth mFirebaseAuth;
    private Button mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_page_fragment, null);
        initView(view);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mEmailTextView = view.findViewById(R.id.edit_emailid_register);
        mPassTextView = view.findViewById(R.id.edit_password_register);
        mRegisterButton = view.findViewById(R.id.button_register);


        mRegisterButton.setOnClickListener(v -> {
            String emailTxt = mEmailTextView.getText().toString();
            String passTxt = mPassTextView.getText().toString();
            if (emailTxt.isEmpty()) {
                mEmailTextView.setError("Please Enter Email");
                mEmailTextView.requestFocus();
            } else if (passTxt.isEmpty()) {
                mPassTextView.setError("Please Enter Password");
                mPassTextView.requestFocus();
            } else {
                            mFirebaseAuth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        navigateToLoginActivity();
                                    }
                                }
                            });

                    }
        });


        loginNavigatePageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginActivity();
            }
        });
        return view;
    }

    private void initView(View view) {
        loginNavigatePageText = view.findViewById(R.id.text_login_page_navigation);
    }

    private void navigateToLoginActivity() {
        getActivity().finish();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
