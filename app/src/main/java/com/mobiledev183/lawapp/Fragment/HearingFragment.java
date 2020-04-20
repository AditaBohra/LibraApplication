package com.mobiledev183.lawapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev183.lawapp.Activity.CaseDetailsActivity;
import com.mobiledev183.lawapp.Adapter.HearingAdapter;
import com.mobiledev183.lawapp.Model.CaseModel;
import com.mobiledev183.lawapp.Model.HearingModel;
import com.mobiledev183.lawapp.ProgressDialogData;
import com.mobiledev183.lawapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class HearingFragment extends Fragment implements HearingAdapter.OnItemClickListener
{
    private RecyclerView mCaseListrecyclerView;
    private HearingAdapter mHearingAdapter;
    private ArrayList<HearingModel> mHearingList;
    private ImageView addHearingButton;
    private FirebaseDatabase mDatabase;
    private ProgressDialogData progressDialogData;
    private TextView hearingsEmptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.hearing_fragment,null);

        progressDialogData = new ProgressDialogData(getActivity());
        progressDialogData.show();
        mCaseListrecyclerView = view.findViewById(R.id.recycler_hearing_view);
        addHearingButton = view.findViewById(R.id.add_hearing_button);
        hearingsEmptyText = view.findViewById(R.id.hearings_empty_text);

        addHearingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddHearingFragment());
            }
        });


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_role", Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString("role",null);

        if (userRole != null && !userRole.equals("advocate")){
            addHearingButton.setVisibility(View.GONE);
        }

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Cases");



        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CaseModel> caseModelList = new ArrayList<>();
                CaseModel caseModel;
                mHearingList = new ArrayList<>();
                List<HearingModel> tempHearing = null;
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.hasChild("hearings")) {
                        mHearingList.clear();
                        caseModel = dataSnapshot1.getValue(CaseModel.class);
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("hearings").getChildren()) {
                            HearingModel hearingModel = dataSnapshot2.getValue(HearingModel.class);
                            if (hearingModel != null) {
                                mHearingList.add(hearingModel);
                                 tempHearing = new ArrayList<>(mHearingList);

                            }
                        }

                        if (caseModel != null) {
                            caseModel.setHearings(tempHearing);
                            caseModelList.add(caseModel);
                        }
                    }


                }
                if (caseModelList.isEmpty()){
                    hearingsEmptyText.setVisibility(View.VISIBLE);
                }
                else {
                    hearingsEmptyText.setVisibility(View.GONE);

                    Collections.sort(caseModelList,Collections.reverseOrder(new Comparator<CaseModel>() {
                        @Override
                        public int compare(CaseModel caseModel, CaseModel t1) {
                            return caseModel.getHearingDate().compareTo(t1.getHearingDate());
                        }
                    }));

                    mHearingAdapter = new HearingAdapter(getActivity(), caseModelList, HearingFragment.this);
                    mCaseListrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    mCaseListrecyclerView.setAdapter(mHearingAdapter);
                }


                progressDialogData.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(CaseModel caseModel) {
        Intent intent = new Intent(getActivity(), CaseDetailsActivity.class);
        intent.putExtra("bundle", caseModel);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
