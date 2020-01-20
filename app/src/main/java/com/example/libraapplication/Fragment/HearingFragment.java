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

import java.util.ArrayList;

public class HearingFragment extends Fragment implements HearingAdapter.OnItemClickListener
{
    private RecyclerView mCaseListrecyclerView;
    private HearingAdapter mHearingAdapter;
    private ArrayList<HearingModel> mHearingList;
    private ImageView addHearingButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.hearing_fragment,null);

        mHearingList = new ArrayList<>();
        mCaseListrecyclerView = view.findViewById(R.id.recycler_hearing_view);
        addHearingButton = view.findViewById(R.id.add_hearing_button);


        addHearingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddHearingFragment());
            }
        });

        HearingModel hearingModel = new HearingModel();
        hearingModel.setParty1("Vashi Police Station");
        hearingModel.setParty2("Gangaram RamSingh Verma");
        hearingModel.setText1("R.C.C./1100095/2002");
        hearingModel.setText2("Civil Court Junior Division, Vashi");
        hearingModel.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel);


        HearingModel hearingModel1 = new HearingModel();
        hearingModel1.setParty1("Vashi Police Station");
        hearingModel1.setParty2("Gangaram RamSingh Verma");
        hearingModel1.setText1("R.C.C./1100095/2002");
        hearingModel1.setText2("Civil Court Junior Division, Vashi");
        hearingModel1.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel1);


        HearingModel hearingModel2 = new HearingModel();
        hearingModel2.setParty1("Vashi Police Station");
        hearingModel2.setParty2("Gangaram RamSingh Verma");
        hearingModel2.setText1("R.C.C./1100095/2002");
        hearingModel2.setText2("Civil Court Junior Division, Vashi");
        hearingModel2.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel2);


        HearingModel hearingModel3 = new HearingModel();
        hearingModel3.setParty1("Vashi Police Station");
        hearingModel3.setParty2("Gangaram RamSingh Verma");
        hearingModel3.setText1("R.C.C./1100095/2002");
        hearingModel3.setText2("Civil Court Junior Division, Vashi");
        hearingModel3.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel3);


        HearingModel hearingModel4 = new HearingModel();
        hearingModel4.setParty1("Vashi Police Station");
        hearingModel4.setParty2("Gangaram RamSingh Verma");
        hearingModel4.setText1("R.C.C./1100095/2002");
        hearingModel4.setText2("Civil Court Junior Division, Vashi");
        hearingModel4.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel4);


        HearingModel hearingModel5 = new HearingModel();
        hearingModel5.setParty1("Vashi Police Station");
        hearingModel5.setParty2("Gangaram RamSingh Verma");
        hearingModel5.setText1("R.C.C./1100095/2002");
        hearingModel5.setText2("Civil Court Junior Division, Vashi");
        hearingModel5.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel5);


        HearingModel hearingModel6 = new HearingModel();
        hearingModel6.setParty1("Vashi Police Station");
        hearingModel6.setParty2("Gangaram RamSingh Verma");
        hearingModel6.setText1("R.C.C./1100095/2002");
        hearingModel6.setText2("Civil Court Junior Division, Vashi");
        hearingModel6.setText3("12th Jt. C.j.j.d And J.m.f.c Vashi Navi Mumbai");
        mHearingList.add(hearingModel6);

        setCaseListAdapter();

        return view;
    }

    private void setCaseListAdapter() {
        mCaseListrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mHearingAdapter = new HearingAdapter(getActivity(), mHearingList,this);
        mCaseListrecyclerView.setAdapter(mHearingAdapter);
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
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
