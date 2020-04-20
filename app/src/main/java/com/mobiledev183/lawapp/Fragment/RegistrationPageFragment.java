package com.mobiledev183.lawapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiledev183.lawapp.Activity.LoginActivity;
import com.mobiledev183.lawapp.Model.UsersModel;
import com.mobiledev183.lawapp.ProgressDialogData;
import com.mobiledev183.lawapp.R;

import java.util.Objects;

public class RegistrationPageFragment extends Fragment {

    private TextView loginNavigatePageText;
    private TextView mEmailTextView;
    private TextView mPassTextView;
    private FirebaseAuth mFirebaseAuth;
    private Button mRegisterButton;
    private ProgressDialogData progressDialogData;
    private EditText mUsername;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private String selectedRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_page_fragment, null);
        initView(view);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        mEmailTextView = view.findViewById(R.id.edit_emailid_register);
        mPassTextView = view.findViewById(R.id.edit_password_register);
        mRegisterButton = view.findViewById(R.id.button_register);
        mUsername = view.findViewById(R.id.edit_username);

        progressDialogData = new ProgressDialogData(getActivity());
        Bundle bundle = getArguments();
        selectedRole = bundle.getString("selectedRole");
        mRegisterButton.setOnClickListener(v -> {
            progressDialogData.show();
            String emailTxt = mEmailTextView.getText().toString();
            String passTxt = mPassTextView.getText().toString();
            String userName = mUsername.getText().toString();
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
                                        progressDialogData.dismiss();
                                    } else {
                                        progressDialogData.dismiss();
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName).build();
                                        if (user != null) {
                                            user.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(task1 -> {
                                                        if (task1.isSuccessful()) {
                                                            Log.d("Registration Page :", "User profile updated.");
                                                        }
                                                    });
                                        }

//
                                        UsersModel usersModel = new UsersModel();
                                        usersModel.setEuid(user.getUid());
                                        usersModel.setEmail(user.getEmail());
                                        usersModel.setSelectedRole(selectedRole);
                                        usersModel.setUname(userName);
                                        mDatabaseRef.child("Users").child(user.getUid()).setValue(usersModel);

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
