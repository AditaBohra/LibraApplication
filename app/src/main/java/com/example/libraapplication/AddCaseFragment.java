package com.example.libraapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddCaseFragment extends Fragment
{
    private ImageView imgAddPartyName;
    private ImageView imgAddCaseNoCard;
    private ImageView imgAddPartyNameTick;
    private ImageView imgAddCaseNoCardTick;
    private Button btnContinue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_case_fragment,null);

        imgAddPartyName = view.findViewById(R.id.img_add_party_name);
        imgAddCaseNoCard = view.findViewById(R.id.add_case_number_card);
        imgAddCaseNoCardTick = view.findViewById(R.id.add_case_number_card_green_tick);
        imgAddPartyNameTick = view.findViewById(R.id.img_add_party_name_green_tick);
        btnContinue = view.findViewById(R.id.btn_add_case_continue);

        imgAddPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAddPartyNameTick.setVisibility(View.VISIBLE);
                imgAddCaseNoCardTick.setVisibility(View.GONE);
            }
        });

        imgAddCaseNoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAddPartyNameTick.setVisibility(View.GONE);
                imgAddCaseNoCardTick.setVisibility(View.VISIBLE);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddCaseFragment1());
            }
        });
        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
