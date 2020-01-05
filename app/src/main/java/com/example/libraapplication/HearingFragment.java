package com.example.libraapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HearingFragment extends Fragment implements CaseAdapter.OnItemClickListener
{
    private RecyclerView mCaseListrecyclerView;
    private CaseAdapter mCaseAdapter;
    private ArrayList<CaseModel> mCaseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_dash_board,null);

        mCaseList = new ArrayList<>();
        mCaseListrecyclerView = view.findViewById(R.id.recycler_case_view);

        CaseModel caseModel = new CaseModel();
        caseModel.setParty1("Vashi Police Station");
        caseModel.setParty2("Gangaram RamSingh Verma");
        caseModel.setText1("R.C.C./1100095/2002");
        caseModel.setText2("Civil Court Junior Division, Vashi");
        caseModel.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel);


        CaseModel caseModel1 = new CaseModel();
        caseModel1.setParty1("Vashi Police Station");
        caseModel1.setParty2("Gangaram RamSingh Verma");
        caseModel1.setText1("R.C.C./1100095/2002");
        caseModel1.setText2("Civil Court Junior Division, Vashi");
        caseModel1.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel1);


        CaseModel caseModel2 = new CaseModel();
        caseModel2.setParty1("Vashi Police Station");
        caseModel2.setParty2("Gangaram RamSingh Verma");
        caseModel2.setText1("R.C.C./1100095/2002");
        caseModel2.setText2("Civil Court Junior Division, Vashi");
        caseModel2.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel2);


        CaseModel caseModel3 = new CaseModel();
        caseModel3.setParty1("Vashi Police Station");
        caseModel3.setParty2("Gangaram RamSingh Verma");
        caseModel3.setText1("R.C.C./1100095/2002");
        caseModel3.setText2("Civil Court Junior Division, Vashi");
        caseModel3.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel3);


        CaseModel caseModel4 = new CaseModel();
        caseModel4.setParty1("Vashi Police Station");
        caseModel4.setParty2("Gangaram RamSingh Verma");
        caseModel4.setText1("R.C.C./1100095/2002");
        caseModel4.setText2("Civil Court Junior Division, Vashi");
        caseModel4.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel4);


        CaseModel caseModel5 = new CaseModel();
        caseModel5.setParty1("Vashi Police Station");
        caseModel5.setParty2("Gangaram RamSingh Verma");
        caseModel5.setText1("R.C.C./1100095/2002");
        caseModel5.setText2("Civil Court Junior Division, Vashi");
        caseModel5.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel5);


        CaseModel caseModel6 = new CaseModel();
        caseModel6.setParty1("Vashi Police Station");
        caseModel6.setParty2("Gangaram RamSingh Verma");
        caseModel6.setText1("R.C.C./1100095/2002");
        caseModel6.setText2("Civil Court Junior Division, Vashi");
        caseModel6.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mCaseList.add(caseModel6);

        setCaseListAdapter();

        return view;
    }

    private void setCaseListAdapter() {
        mCaseListrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mCaseAdapter = new CaseAdapter(getActivity(), mCaseList,this);
        mCaseListrecyclerView.setAdapter(mCaseAdapter);
    }

    @Override
    public void onItemClick(CaseModel caseModel) {
        Intent intent = new Intent(getActivity(),CaseDetailsActivity.class);
        intent.putExtra("bundle",caseModel);
        startActivity(intent);
    }
}
