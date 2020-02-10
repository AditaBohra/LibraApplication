package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Activity.AddCaseActivity;
import com.example.libraapplication.Activity.CaseDetailsActivity;
import com.example.libraapplication.Activity.HomeActivity;
import com.example.libraapplication.Adapter.CaseAdapter;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.ProgressDialogData;
import com.example.libraapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                mCaseList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    CaseModel caseModel = dataSnapshot1.getValue(CaseModel.class);
                    if(caseModel != null ) {
                        mCaseList.add(caseModel);
                    }
                }
                if (mCaseList.isEmpty()){
                    casesEmptyText.setVisibility(View.VISIBLE);
                }
                else {
                    casesEmptyText.setVisibility(View.GONE);
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
//        Intent caseDetailsActivityIntent = new Intent(getActivity(), CaseDetailsActivity.class);
//        caseDetailsActivityIntent.putExtra("bundle",caseModel);
//        startActivity(caseDetailsActivityIntent);
    }

}
