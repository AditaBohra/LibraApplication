package com.mobiledev183.lawapp.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev183.lawapp.Fragment.AlertFragment;
import com.mobiledev183.lawapp.Fragment.CaseFragment;
import com.mobiledev183.lawapp.Fragment.TabFragment;
import com.mobiledev183.lawapp.Model.UsersModel;
import com.mobiledev183.lawapp.ProgressDialogData;
import com.mobiledev183.lawapp.R;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private FirebaseDatabase mDatabase;
    private ProgressDialogData progressDialogData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mBottomNavigationView.setSelectedItemId(R.id.action_home);

        progressDialogData = new ProgressDialogData(this);
        progressDialogData.show();

        getUserDetails();
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    loadFragment(new TabFragment());
                    return true;
                case R.id.action_cases:
                    loadFragment(new CaseFragment());
                    return true;
                case R.id.action_alerts:
                    loadFragment(new AlertFragment());
                    return true;
            }
            return false;
        });


    }

    private void getUserDetails() {

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersModel user = dataSnapshot.getValue(UsersModel.class);
                SharedPreferences sharedPreferences = getSharedPreferences("user_role",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("role",user.getSelectedRole());
                editor.commit();
                loadFragment(new TabFragment());
                progressDialogData.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialogData.dismiss();
            }
        });


    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getCurrentFragment() instanceof  CaseFragment || getCurrentFragment()instanceof AlertFragment){
            loadFragment(new TabFragment());
        }
        else {
            super.onBackPressed();
        }

    }

    private Fragment getCurrentFragment()
    {
        return getSupportFragmentManager()
                .findFragmentById(R.id.container);
    }
}
