package com.example.libraapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.Model.EuidModel;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.ProgressDialogData;
import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddHearingFragment extends Fragment{

    private EditText edit_add_judges;
    private TextView hearing_date_tv;
    private Calendar mCalender;
    private Button continueBtn;
    private FirebaseDatabase mDatabase;
    private static ArrayList<String> caseNoList;
    private static final String TAG = AddHearingFragment.class.getSimpleName();
    private DatabaseReference mDatabaseRef;
    private ProgressDialogData progressDialogData;
    private Spinner spinner;
    ArrayList<String> euids;
    private ArrayList<CaseModel> caseModelArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_hearing_fragment,null);
        edit_add_judges = view.findViewById(R.id.add_judges);
        hearing_date_tv = view.findViewById(R.id.edit_hearing_date);
        continueBtn = view.findViewById(R.id.hearing_continue_btn);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group_category_hearing);
        mCalender = Calendar.getInstance();
        spinner = view.findViewById(R.id.case_no_spinner);
        progressDialogData = new ProgressDialogData(getActivity());
        progressDialogData.show();

        getSpinnerData(spinner);

        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateEditText(hearing_date_tv, mCalender);
        };

        hearing_date_tv.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(getActivity()),
                date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                loadFragment(new TabFragment());
                return true;
            }
            return false;
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id;
                String radioButtonText, judge, case_no, hearing_date;

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    id = radioGroup.getCheckedRadioButtonId();
                    if (((RadioButton) view.findViewById(id)).getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please select case status ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Please select case status ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isValidateForm()) {
                    id = radioGroup.getCheckedRadioButtonId();
                    radioButtonText = ((RadioButton) view.findViewById(id)).getText().toString();
                    judge = edit_add_judges.getText().toString();
                    case_no = spinner.getSelectedItem().toString();
                    hearing_date = hearing_date_tv.getText().toString();

                } else {
                    Toast.makeText(getActivity(), "Please Enter Details..", Toast.LENGTH_SHORT).show();
                    return;
                }


                HearingModel hearingModel = new HearingModel();
                hearingModel.setCase_no(case_no);
                hearingModel.setCategory(radioButtonText);
                hearingModel.setJudge_name(judge);
                hearingModel.setHearing_date(hearing_date);

                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mDatabase.getReference();

                euids = getEuids(hearingModel.getCase_no());

                if (euids != null && euids.size() > 0){
                    for (String euid : euids){
                        if (!euid.equals(FirebaseAuth.getInstance().getUid())){
                            for (CaseModel caseModel: caseModelArrayList){
                                if (caseModel.getCaseNo().equals(hearingModel.getCase_no())) {
                                    mDatabaseRef.child(euid)
                                            .child("Cases").child(caseModel.getUuid()).child("hearings").push().setValue(hearingModel);
                                }
                            }
                        }
                    }

                }
                for (CaseModel caseModel: caseModelArrayList){
                    if (caseModel.getCaseNo().equals(hearingModel.getCase_no())){
                        mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                                .child("Cases").child(caseModel.getUuid()).child("hearings").push().setValue(hearingModel);
                    }

                }


                loadFragment(new TabFragment());

            }
        });


        return  view;
    }

    private ArrayList<String> getEuids(String case_no)
    {
        for (CaseModel caseModel: caseModelArrayList){
            if (caseModel.getCaseNo().equals(case_no)){
                return caseModel.getAllTeamEuidList();
            }
        }
        return null;
    }

    private boolean isValidateForm() {
        if (!edit_add_judges.getText().toString().isEmpty() && spinner.getSelectedItem() != null &&
                !hearing_date_tv.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void getSpinnerData(Spinner spinner){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Cases");



        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caseNoList = new ArrayList<>();
                caseModelArrayList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    CaseModel caseModel = dataSnapshot1.getValue(CaseModel.class);
                    caseModelArrayList.add(caseModel);
                    String caseNumber = caseModel.getCaseNo();
                    if(caseNumber!= null && !caseNumber.isEmpty()) {
                        caseNoList.add(caseNumber);
                    }
                }
                ArrayAdapter<String> caseNoAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_spinner_item, caseNoList);
                caseNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(caseNoAdapter);

                progressDialogData.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                //Do nothing
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        case_date.setText(sdf.format(mCalender.getTime()));
    }


}
