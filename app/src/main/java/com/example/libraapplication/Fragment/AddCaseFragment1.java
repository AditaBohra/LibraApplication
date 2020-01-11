package com.example.libraapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AddCaseFragment1 extends Fragment {
    private EditText editHighCourt;
    private EditText editRespondent;
    private Spinner spinnerSide;
    private EditText editRegisteredYear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_case_fragment1, null);

        editHighCourt = view.findViewById(R.id.edit_high_court);
        editRespondent = view.findViewById(R.id.edit_respondent);
        spinnerSide = view.findViewById(R.id.spinner_side);

        List<String> arrayList = new ArrayList<>();
        arrayList.add("Civil");
        arrayList.add("Criminal");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSide.setAdapter(dataAdapter);
        return view;
    }

}
