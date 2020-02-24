package com.example.libraapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Model.TaskModel;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<TaskModel> mTaskList;
    private TaskClickListener taskClickListener;

    public interface TaskClickListener{
        void onEditClick(TaskModel taskModel);
        void onDeleteClick (TaskModel taskModel);
    }
    public TaskListAdapter(Context context, ArrayList<TaskModel> taskList, TaskClickListener taskClickListener) {
        mContext = context;
        mTaskList = taskList;
        this.taskClickListener = taskClickListener;
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
        holder.title.setText("Title: "+taskModel.getTitle());
        holder.description.setText("Task Description: "+taskModel.getDesc());
        holder.mydate.setText(taskModel.getDate());
        holder.assignTo.setText("Assign To: "+taskModel.getAssignto());
        holder.notes.setText("Note: "+taskModel.getNote());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskClickListener.onEditClick(taskModel);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskClickListener.onDeleteClick(taskModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView mydate;
        private TextView assignTo;
        private ImageView imgEdit;
        private ImageView imgDelete;
        private TextView notes;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            description = itemView.findViewById(R.id.display_task_desc_text);
            mydate = itemView.findViewById(R.id.display_date_text);
            assignTo = itemView.findViewById(R.id.display_task_assignto_text);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);
            notes = itemView.findViewById(R.id.display_task_notes_text);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
