package com.example.libraapplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Adapter.CaseAdapter;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;
import com.example.libraapplication.Fragment.SearchFragment;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity implements CaseAdapter.OnItemClickListener {

    private RecyclerView mCaseListrecyclerView;
    private CaseAdapter mCaseAdapter;
    private ArrayList<CaseModel> mCaseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mCaseList = new ArrayList<>();
        mCaseListrecyclerView = findViewById(R.id.recycler_case_view);

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
        loadFragment(new SearchFragment());

    }

    private void setCaseListAdapter() {
        mCaseListrecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mCaseAdapter = new CaseAdapter(this, mCaseList,this);
        mCaseListrecyclerView.setAdapter(mCaseAdapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_framelayout_container, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(CaseModel caseModel) {

    }
}
