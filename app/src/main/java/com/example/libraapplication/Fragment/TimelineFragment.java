package com.example.libraapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Activity.CaseDetailsActivity;
import com.example.libraapplication.Adapter.TimeLineAdapter;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.Model.Timeline;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class TimelineFragment extends Fragment implements CaseDetailsActivity.OnCaseDetailListener {

    private ArrayList<Timeline> timelineArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CaseDetailsActivity caseDetailsActivity;
    private TextView empty_time_line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.timeline_fragment,null);
        recyclerView = view.findViewById(R.id.timeline_recyclerview);
        empty_time_line = view.findViewById(R.id.timeline_empty_text);

        caseDetailsActivity = (CaseDetailsActivity) getActivity();
        caseDetailsActivity.setOnCaseDetailListener(this);


        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(getActivity(),timelineArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(timeLineAdapter);

        return view;
    }

    @Override
    public void setCaseDeatails(CaseModel caseModel) {
        if(caseModel.getHearingModels() != null) {
            for (HearingModel hearingModel : caseModel.getHearingModels()) {
                Timeline timeline = new Timeline();
                timeline.setJudge_name(hearingModel.getJudge_name());
//                timeline.setRoom_name(hearingModel.);
                timeline.setDate(hearingModel.getHearing_date());
                timelineArrayList.add(timeline);
            }
        }
        else {
            empty_time_line.setVisibility(View.VISIBLE);
        }
    }
}
