package com.example.libraapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Adapter.TimeLineAdapter;
import com.example.libraapplication.Model.Timeline;
import com.example.libraapplication.R;

import java.sql.Time;
import java.util.ArrayList;

public class TimelineFragment extends Fragment {

    private ArrayList<Timeline> timelineArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.timeline_fragment,null);
        recyclerView = view.findViewById(R.id.timeline_recyclerview);

        Timeline timeline = new Timeline();
        timeline.setDate("1 Jan 19");
        timeline.setRoom_name("Goa");
        timeline.setJudge_name("K.P.S");
        timelineArrayList.add(timeline);

        Timeline timeline2 = new Timeline();
        timeline2.setDate("1 May 19");
        timeline2.setRoom_name("Mumbai");
        timeline2.setJudge_name("K.P.S");
        timelineArrayList.add(timeline2);

        Timeline timeline3 = new Timeline();
        timeline3.setDate("7 Jan 19");
        timeline3.setRoom_name("Kashmir");
        timeline3.setJudge_name("K.P.S");
        timelineArrayList.add(timeline3);

        Timeline timeline4 = new Timeline();
        timeline4.setDate("7 May 19");
        timeline4.setRoom_name("USA");
        timeline4.setJudge_name("K.P.S");
        timelineArrayList.add(timeline4);

        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(getActivity(),timelineArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(timeLineAdapter);

        return view;
    }
}
