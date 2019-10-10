package com.example.libraapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SelectRoleFragment extends Fragment {
    private Button continueButton;
    private LinearLayout advocateRoleLayout;
    private ConstraintLayout civilianRoleLayout;
    private boolean isAdvocate;
    private boolean isCivilian;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_role_fragment, null);
        initView(view);

        // continue button click......
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRegistrationFragment(new RegistrationPageFragment());
            }
        });

        // advocate layout click
        advocateRoleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCivilian) {
                    civilianRoleLayout.setBackground(getActivity().getResources().getDrawable
                            (R.drawable.rounded_white_background));
                    isCivilian = false;
                }
                advocateRoleLayout.setBackground(getActivity().getResources().getDrawable
                        (R.drawable.rounded_lightyellow_background));
                isAdvocate = true;
            }
        });


        // civilian layout click
        civilianRoleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdvocate) {
                    advocateRoleLayout.setBackground(getActivity().getResources().getDrawable
                            (R.drawable.rounded_white_background));
                    isAdvocate = false;
                }
                civilianRoleLayout.setBackground(getActivity().getResources().getDrawable
                        (R.drawable.rounded_lightyellow_background));
                isCivilian = true;
            }
        });

        return view;
    }

    private void initView(View view) {
        continueButton = view.findViewById(R.id.button_continue);
        advocateRoleLayout = view.findViewById(R.id.layout_role1);
        civilianRoleLayout = view.findViewById(R.id.layout_role2);
    }

    private void loadRegistrationFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.registartion_conatiner, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


}
