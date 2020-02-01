package com.example.libraapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class AddCaseFragment1 extends Fragment {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private Calendar mCalender;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_case_fragment1, null);

        Button continueButton = view.findViewById(R.id.continueBtn);

        EditText case_no = view.findViewById(R.id.edit_case_no);
        EditText case_court_name = view.findViewById(R.id.edit_court_name);
        EditText case_edit_judge_name = view.findViewById(R.id.edit_judge);
        EditText case_party1 = view.findViewById(R.id.edit_party1);
        EditText case_party2 = view.findViewById(R.id.edit_party2);
        EditText case_lawyer = view.findViewById(R.id.edit_lawyer);
        EditText case_team = view.findViewById(R.id.edit_select_team);
        RadioGroup case_category = view.findViewById(R.id.radio_group_category);
        TextView case_date = view.findViewById(R.id.edit_case_date);
        mCalender = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateEditText(case_date, mCalender);
        };

        case_date.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(getActivity()),
                date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show());


        continueButton.setOnClickListener(v -> {
            int id = case_category.getCheckedRadioButtonId();
            String radioButtonText = ((RadioButton) view.findViewById(id)).getText().toString();
            String case_number = case_no.getText().toString();
            String court_name = case_court_name.getText().toString();
            String case_judge_name = case_edit_judge_name.getText().toString();
            String party1 = case_party1.getText().toString();
            String party2 = case_party2.getText().toString();
            String lawyer = case_lawyer.getText().toString();
            String team = case_team.getText().toString();
            String case_date_text = case_date.getText().toString();


            CaseModel caseModel = new CaseModel();
            caseModel.setCaseNo(case_number);
            caseModel.setCourtName(court_name);
            caseModel.setJudgeName(case_judge_name);
            caseModel.setParty1(party1);
            caseModel.setParty2(party2);
            caseModel.setLawyer(lawyer);
            caseModel.setTeam(team);
            if (!radioButtonText.isEmpty()) {
                caseModel.setStatus(radioButtonText);
            }
            caseModel.setHearingDate(case_date_text);

            mDatabase = FirebaseDatabase.getInstance();
            mDatabaseRef = mDatabase.getReference();


            mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                    .child("Cases").child(caseModel.getCaseNo()).setValue(caseModel);

            if (getActivity() != null) {
                getActivity().finish();
            }

        });

        return view;
    }

    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        case_date.setText(sdf.format(mCalender.getTime()));
    }
}
