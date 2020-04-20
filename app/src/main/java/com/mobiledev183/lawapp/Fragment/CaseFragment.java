package com.mobiledev183.lawapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev183.lawapp.Activity.AddCaseActivity;
import com.mobiledev183.lawapp.Activity.CaseDetailsActivity;
import com.mobiledev183.lawapp.Activity.HomeActivity;
import com.mobiledev183.lawapp.Adapter.CaseAdapter;
import com.mobiledev183.lawapp.Model.CaseModel;
import com.mobiledev183.lawapp.Model.HearingModel;
import com.mobiledev183.lawapp.ProgressDialogData;
import com.mobiledev183.lawapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CaseFragment extends Fragment implements CaseAdapter.CaseClickListener {

    private Button btnAddCase;
    private ArrayList<CaseModel> mCaseList;
    private RecyclerView recyclerView;
    private CaseAdapter caseAdapter;
    private Toolbar mToolbar;
    private FirebaseDatabase mDatabase;
    public static final String TAG = CaseFragment.class.getSimpleName();
    private ProgressDialogData progressDialogData;
    private TextView casesEmptyText;
    CaseModel mCaseModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.case_fragment, null);
        btnAddCase = view.findViewById(R.id.add_case_button);
        recyclerView = view.findViewById(R.id.recycler_case_view);

        progressDialogData = new ProgressDialogData(getActivity());
        progressDialogData.show();

        mToolbar = view.findViewById(R.id.my_toolbar);
        casesEmptyText = view.findViewById(R.id.cases_empty_text);

        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        mToolbar.setTitle("Cases");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> ((HomeActivity) getActivity()).loadFragment(new TabFragment()));


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_role", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("role",null);

        if (userRole != null && !userRole.equals("advocate")){
            btnAddCase.setVisibility(View.GONE);
        }

        btnAddCase.setOnClickListener(view1 -> {
            Intent caseActivityIntent = new Intent(getActivity(), AddCaseActivity.class);
            startActivity(caseActivityIntent);
        });

        createView();


        return view;

    }

    private void createView(){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Cases");



        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HearingModel> mHearingList = new ArrayList<>();
                List<HearingModel> tempHearing = null;
                mCaseList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    mCaseModel = dataSnapshot1.getValue(CaseModel.class);
                    if(dataSnapshot1.hasChild("hearings")) {
                        mHearingList.clear();
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("hearings").getChildren()) {
                            HearingModel hearingModel = dataSnapshot2.getValue(HearingModel.class);
                            if (hearingModel != null) {
                                mHearingList.add(hearingModel);
                                tempHearing = new ArrayList<>(mHearingList);

                            }
                        }

                        if (mCaseModel != null) {
                            mCaseModel.setHearings(tempHearing);
                        }
                    }
                    mCaseList.add(mCaseModel);
                }
                if (mCaseList.isEmpty()){
                    casesEmptyText.setVisibility(View.VISIBLE);
                }
                else {
                    casesEmptyText.setVisibility(View.GONE);
                    Collections.sort(mCaseList,Collections.reverseOrder(new Comparator<CaseModel>() {
                        @Override
                        public int compare(CaseModel caseModel, CaseModel t1) {
                            return caseModel.getHearingDate().compareTo(t1.getHearingDate());
                        }
                    }));

                    caseAdapter = new CaseAdapter(getActivity(), mCaseList, CaseFragment.this);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recyclerView.setAdapter(caseAdapter);
                }


                progressDialogData.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                //Do nothing
            }
        });
    }


    @Override
    public void onCaseClick(CaseModel caseModel) {
        Intent caseDetailsActivityIntent = new Intent(getActivity(), CaseDetailsActivity.class);
        caseDetailsActivityIntent.putExtra("bundle",caseModel);
        startActivity(caseDetailsActivityIntent);
    }

}
