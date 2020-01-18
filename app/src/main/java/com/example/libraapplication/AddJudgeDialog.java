package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Adapter.JudgesAdapter;

import java.util.ArrayList;

public class AddJudgeDialog extends Dialog implements JudgesAdapter.CheckListener {

    private JudgesAdapter judgesAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> judgesList;
    private UpDateJudgesListListener upDateJudgesListListener;

    public interface UpDateJudgesListListener{
        void updateJudgeList(ArrayList<String> judgesList);
    }

    public AddJudgeDialog(@NonNull Context context, ArrayList<String> judgesList, UpDateJudgesListListener upDateJudgesListListener) {
        super(context);
        this.context = context;
        this.judgesList = judgesList;
        this.upDateJudgesListListener = upDateJudgesListListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_judge_dialog);
        recyclerView = findViewById(R.id.add_judge_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this.context,1));
        judgesAdapter = new JudgesAdapter(this.context,judgesList,this);
        recyclerView.setAdapter(judgesAdapter);
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);

    }

    @Override
    public void onDeselect(ArrayList<String> judgeList) {
        judgesList = judgeList;
        this.upDateJudgesListListener.updateJudgeList(judgesList);
    }
}
