package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraapplication.Activity.AddCaseActivity;
import com.example.libraapplication.R;

public class CaseFragment extends Fragment {

    private Button btnAddCase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.case_fragment,null);
        btnAddCase = view.findViewById(R.id.btn_add_case);

        btnAddCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent caseActivityIntent = new Intent(getActivity(), AddCaseActivity.class);
                startActivity(caseActivityIntent);
            }
        });
        return view;

    }
}
