package com.example.libraapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TaskModel> mTaskList;

    public TaskListAdapter(Context context, ArrayList<TaskModel> taskList) {
        mContext = context;
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.task_list_recyclerview, null);
        return new ViewHolder(constraintLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel taskModel = mTaskList.get(position);
        holder.title.setText(taskModel.getTitle());
        holder.mydate.setText(taskModel.getDate());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView mydate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            mydate = itemView.findViewById(R.id.tasklist_date_text);
        }
    }
}
