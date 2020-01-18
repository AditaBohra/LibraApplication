package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Activity.AddCaseActivity;
import com.example.libraapplication.Adapter.CaseAdapter;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class CaseFragment extends Fragment {

    private ImageView btnAddCase;
    private ArrayList<CaseModel> mCaseList;
    private RecyclerView recyclerView;
    private CaseAdapter caseAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.case_fragment,null);
        btnAddCase = view.findViewById(R.id.add_case_button);
        recyclerView = view.findViewById(R.id.recycler_case_view);
        mCaseList = new ArrayList<>();

        btnAddCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent caseActivityIntent = new Intent(getActivity(), AddCaseActivity.class);
                startActivity(caseActivityIntent);
            }
        });


        CaseModel caseModel = new CaseModel();
        caseModel.setParty1("Vashi Police Station");
        caseModel.setParty2("Gangaram RamSingh Verma");
        caseModel.setCaseNo("R.C.C./1100095/2002");
        caseModel.setCourtName("Civil Court Junior Division, Vashi");
        caseModel.setStatus("Pending");
        caseModel.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel);


        CaseModel caseModel1 = new CaseModel();
        caseModel1.setParty1("Vashi Police Station");
        caseModel1.setParty2("Gangaram RamSingh Verma");
        caseModel1.setCaseNo("R.C.C./1100095/2002");
        caseModel1.setCourtName("Civil Court Junior Division, Vashi");
        caseModel1.setStatus("Pending");
        caseModel1.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel1);


        CaseModel caseModel2 = new CaseModel();
        caseModel2.setParty1("Vashi Police Station");
        caseModel2.setParty2("Gangaram RamSingh Verma");
        caseModel2.setCaseNo("R.C.C./1100095/2002");
        caseModel2.setCourtName("Civil Court Junior Division, Vashi");
        caseModel2.setStatus("Pending");
        caseModel2.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel2);


        CaseModel caseModel3 = new CaseModel();
        caseModel3.setParty1("Vashi Police Station");
        caseModel3.setParty2("Gangaram RamSingh Verma");
        caseModel3.setCaseNo("R.C.C./1100095/2002");
        caseModel3.setCourtName("Civil Court Junior Division, Vashi");
        caseModel3.setHearingDate("Next Hearing: 12 Jan 2020");
        caseModel3.setStatus("Pending");
        mCaseList.add(caseModel3);


        CaseModel caseModel4 = new CaseModel();
        caseModel4.setParty1("Vashi Police Station");
        caseModel4.setParty2("Gangaram RamSingh Verma");
        caseModel4.setCaseNo("R.C.C./1100095/2002");
        caseModel4.setCourtName("Civil Court Junior Division, Vashi");
        caseModel4.setStatus("Pending");
        caseModel4.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel4);


        CaseModel caseModel5 = new CaseModel();
        caseModel5.setParty1("Vashi Police Station");
        caseModel5.setParty2("Gangaram RamSingh Verma");
        caseModel5.setCaseNo("R.C.C./1100095/2002");
        caseModel5.setCourtName("Civil Court Junior Division, Vashi");
        caseModel5.setStatus("Pending");
        caseModel5.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel5);


        CaseModel caseModel6 = new CaseModel();
        caseModel6.setParty1("Vashi Police Station");
        caseModel6.setParty2("Gangaram RamSingh Verma");
        caseModel6.setCaseNo("R.C.C./1100095/2002");
        caseModel6.setCourtName("Civil Court Junior Division, Vashi");
        caseModel6.setStatus("Pending");
        caseModel6.setHearingDate("Next Hearing: 12 Jan 2020");
        mCaseList.add(caseModel6);

        caseAdapter = new CaseAdapter(getActivity(),mCaseList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(caseAdapter);
        return view;

    }
}
