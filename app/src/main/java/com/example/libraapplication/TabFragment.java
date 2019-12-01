package com.example.libraapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabFragment extends Fragment
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_fragment,null);
        mViewPager = view.findViewById(R.id.viewpager);
        setUpViewPager(mViewPager);

        mTabLayout = view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
