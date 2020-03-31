package com.example.libraapplication.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.Model.UsersModel;
import com.example.libraapplication.R;
import com.example.libraapplication.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;


public class AddCaseFragment1 extends Fragment implements Utility.GetUserModelListListener {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private Calendar mCalender;
    private EditText case_no, case_court_name, case_edit_judge_name, case_party1, case_party2, case_lawyer;
    private TextView mselectedTeamText, case_team;
    private RadioGroup case_category;
    private TextView case_date;
    private boolean[] checkedItems;
    private ArrayList<Integer> mSelectedTeamList = new ArrayList<>();
    private String[] userNameList;
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<UsersModel> usersModelArrayList;
    private ArrayList<String> euidList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_case_fragment1, null);

        Button continueButton = view.findViewById(R.id.continueBtn);

        case_no = view.findViewById(R.id.edit_case_no);
        case_court_name = view.findViewById(R.id.edit_court_name);
        case_edit_judge_name = view.findViewById(R.id.edit_judge);
        case_party1 = view.findViewById(R.id.edit_party1);
        case_party2 = view.findViewById(R.id.edit_party2);
        case_lawyer = view.findViewById(R.id.edit_lawyer);
        case_team = view.findViewById(R.id.edit_select_team);
        case_category = view.findViewById(R.id.radio_group_category);
        case_date = view.findViewById(R.id.edit_case_date);
        mCalender = Calendar.getInstance();
        mselectedTeamText = view.findViewById(R.id.selectedTeamText);

        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateEditText(case_date, mCalender);
        };


        case_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createMultiChoiceDialog();

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        case_date.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(getActivity()),
                date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show());


        continueButton.setOnClickListener(v -> {

            int id;
            String radioButtonText = null, case_number = null, court_name = null, case_judge_name = null,
                    party1 = null, party2 = null, lawyer = null, team = null, case_date_text = null;
            if (case_category.getCheckedRadioButtonId() != -1) {
                id = case_category.getCheckedRadioButtonId();
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
                id = case_category.getCheckedRadioButtonId();
                radioButtonText = ((RadioButton) view.findViewById(id)).getText().toString();
                case_number = case_no.getText().toString();
                if (case_number.contains("/")){
                    case_number = case_number.replaceAll("/","_");
                    Toast.makeText(getActivity(), ""+case_number, Toast.LENGTH_SHORT).show();
                }
                court_name = case_court_name.getText().toString();
                case_judge_name = case_edit_judge_name.getText().toString();
                party1 = case_party1.getText().toString();
                party2 = case_party2.getText().toString();
                lawyer = case_lawyer.getText().toString();
                team = mselectedTeamText.getText().toString();
                case_date_text = case_date.getText().toString();
            } else {
                Toast.makeText(getActivity(), "Please Enter Details..", Toast.LENGTH_SHORT).show();
                return;
            }

            String uniqueID = UUID.randomUUID().toString();

            CaseModel caseModel = new CaseModel();
            caseModel.setCaseNo(case_number);
            caseModel.setCourtName(court_name);
            caseModel.setJudgeName(case_judge_name);
            caseModel.setParty1(party1);
            caseModel.setParty2(party2);
            caseModel.setLawyer(lawyer);
            caseModel.setTeam(team);
            caseModel.setEuid(FirebaseAuth.getInstance().getUid());
            if (!radioButtonText.isEmpty()) {
                caseModel.setStatus(radioButtonText);
            }
            caseModel.setHearingDate(case_date_text);
            if (euidList != null && euidList.size() > 0){
                euidList.add(FirebaseAuth.getInstance().getUid());
                caseModel.setAllTeamEuidList(euidList);
            }
            caseModel.setUuid(uniqueID);
            mDatabase = FirebaseDatabase.getInstance();
            mDatabaseRef = mDatabase.getReference();

            // Code to set data to all team members..............
            if (usersModelArrayList.size() > 0){
                for (String euid : euidList){
                    if (euid != FirebaseAuth.getInstance().getUid()){
                        caseModel.setEuid(euid);
                        mDatabaseRef.child(euid)
                                .child("Cases").child(uniqueID).setValue(caseModel);
                    }
                }

            }
            mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                    .child("Cases").child(uniqueID).setValue(caseModel);


            if (getActivity() != null) {
                getActivity().finish();
            }

        });
        Utility utility = new Utility(this);
        utility.getAllUserModelList();
        return view;
    }

    private void createMultiChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please select your team");
        builder.setMultiChoiceItems(userNameList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked){
                    if (!mSelectedTeamList.contains(position)){
                        mSelectedTeamList.add(position);
                        euidList.add(usersModelArrayList.get(position).getEuid());
                    }
                    else {

                        for (int i = 0; i < mSelectedTeamList.size(); i++) {
                            if (mSelectedTeamList.get(i) == position) {
                                mSelectedTeamList.remove(i);
                            }
                        }
                        euidList.remove(usersModelArrayList.get(position).getEuid());
                    }
                }
                else{
                    for (int i = 0; i < mSelectedTeamList.size(); i++) {
                        if (mSelectedTeamList.get(i) == position) {
                            mSelectedTeamList.remove(i);
                        }
                    }
                    euidList.remove(usersModelArrayList.get(position).getEuid());
                }
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i< mSelectedTeamList.size(); i++){
                    item = item + userNameList[mSelectedTeamList.get(i)];
                    if (i != mSelectedTeamList.size() - 1){
                        item = item + ", ";
                    }
                }
                if (mSelectedTeamList.size() > 0){
                    mselectedTeamText.setVisibility(View.VISIBLE);
                }
                else {
                    mselectedTeamText.setVisibility(View.GONE);
                }
                mselectedTeamText.setText(item);
            }
        });

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mSelectedTeamList.size() > 0){
                    mselectedTeamText.setVisibility(View.VISIBLE);
                }
                else {
                    mselectedTeamText.setVisibility(View.GONE);
                }
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i=0; i < checkedItems.length; i++){
                    checkedItems[i] = false;
                    mSelectedTeamList.clear();
                    euidList.clear();
                    mselectedTeamText.setText("");
                    mselectedTeamText.setVisibility(View.GONE);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String[] getStringArray(ArrayList<String> arr)
    {

        String str[] = new String[arr.size()];

        for (int j = 0; j < arr.size(); j++) {

            str[j] = arr.get(j);
        }

        return str;
    }

    private boolean isValidateForm() {
        if (!case_no.getText().toString().isEmpty() && !case_court_name.getText().toString().isEmpty() && !case_edit_judge_name.getText().toString().isEmpty()
                && !case_party1.getText().toString().isEmpty() && !case_party2.getText().toString().isEmpty() && !case_lawyer.getText().toString().isEmpty() &&
                !mselectedTeamText.getText().toString().isEmpty() && !case_date.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        case_date.setText(sdf.format(mCalender.getTime()));
    }



    @Override
    public void getUserList(ArrayList<UsersModel> usersModelArrayList) {
        setTeamListAdapter(usersModelArrayList);
    }

    void setTeamListAdapter(ArrayList<UsersModel> usersModelArrayList) {
        Toast.makeText(getActivity(), "Data Recieved", Toast.LENGTH_SHORT).show();
        for (UsersModel usersModel : usersModelArrayList) {
            users.add(usersModel.getUname());
        }
        this.usersModelArrayList = usersModelArrayList;
        userNameList = getStringArray(users);
        checkedItems = new boolean[users.size()];
    }
}
