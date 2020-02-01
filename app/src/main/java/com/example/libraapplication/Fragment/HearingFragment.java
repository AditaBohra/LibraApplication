package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Activity.CaseDetailsActivity;
import com.example.libraapplication.Adapter.HearingAdapter;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HearingFragment extends Fragment implements HearingAdapter.OnItemClickListener
{
    private RecyclerView mCaseListrecyclerView;
    private HearingAdapter mHearingAdapter;
    private ArrayList<HearingModel> mHearingList;
    private ImageView addHearingButton;
    private FirebaseDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.hearing_fragment,null);


        mCaseListrecyclerView = view.findViewById(R.id.recycler_hearing_view);
        addHearingButton = view.findViewById(R.id.add_hearing_button);


        addHearingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddHearingFragment());
            }
        });

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("hearings");



        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mHearingList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    HearingModel hearingModel = dataSnapshot1.getValue(HearingModel.class);
                    if(hearingModel != null){
                        mHearingList.add(hearingModel);
                    }

                }
                mHearingAdapter = new HearingAdapter(getActivity(), mHearingList, HearingFragment.this);
                mCaseListrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                mCaseListrecyclerView.setAdapter(mHearingAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(HearingModel hearingModel) {
        Intent intent = new Intent(getActivity(), CaseDetailsActivity.class);
        intent.putExtra("bundle", hearingModel);
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
