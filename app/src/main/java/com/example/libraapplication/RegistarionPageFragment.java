package com.example.libraapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegistarionPageFragment extends Fragment {

    private TextView loginNavigatePageText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_page_fragment,null);
        initView(view);
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
