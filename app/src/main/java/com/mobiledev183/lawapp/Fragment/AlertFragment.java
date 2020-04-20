package com.mobiledev183.lawapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mobiledev183.lawapp.Activity.HomeActivity;
import com.mobiledev183.lawapp.R;

public class AlertFragment extends Fragment {

    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_fragment,null);


        mToolbar = view.findViewById(R.id.my_toolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setTitle("Alerts");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> ((HomeActivity) getActivity()).loadFragment(new TabFragment()));

        return view;
    }
}