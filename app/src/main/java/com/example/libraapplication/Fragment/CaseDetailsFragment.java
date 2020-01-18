package com.example.libraapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.libraapplication.Adapter.CaseDetailsViewpagerAdapter;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class CaseDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_case_details,null);
        ViewPager viewPager = view.findViewById(R.id.case_details_viewpager);
        setUpViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.case_details_tabs);
        tabLayout.setupWithViewPager(viewPager);

        if(getArguments() != null){
            HearingModel hearingModel = (HearingModel) getArguments().getSerializable("bundle");
        }


        return view;




    }
    private void setUpViewPager(ViewPager viewPager) {

        CaseDetailsViewpagerAdapter viewPagerAdapter = new CaseDetailsViewpagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), 0);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
