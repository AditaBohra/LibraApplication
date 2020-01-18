package com.example.libraapplication.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.libraapplication.Fragment.AppointmentFragment;
import com.example.libraapplication.Fragment.TasksFragment;
import com.example.libraapplication.Fragment.TimelineFragment;

public class CaseDetailsViewpagerAdapter  extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Timeline", "Docs","Notes","Notify"};

    public CaseDetailsViewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimelineFragment();
            case 1:
                return new TasksFragment();
            case 2:
                return new AppointmentFragment();
            case 3:
                return new AppointmentFragment();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
