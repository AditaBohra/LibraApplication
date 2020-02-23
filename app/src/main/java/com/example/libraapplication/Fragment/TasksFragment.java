package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Database.TaskDBHelper;
import com.example.libraapplication.Model.AppointmentModel;
import com.example.libraapplication.R;
import com.example.libraapplication.TaskDialogBox;
import com.example.libraapplication.Adapter.TaskListAdapter;
import com.example.libraapplication.Model.TaskModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TasksFragment extends Fragment implements TaskListAdapter.TaskClickListener, TaskDialogBox.TaskDismissListener {
    private TaskListAdapter mTaskListAdapter;
    private RecyclerView mTaskListRecyclerView;
    private Button mAddTaskButton;
    private ArrayList<TaskModel> mTaskList;
    private TaskDBHelper dbHelper;
    private TaskDialogBox mDialogBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tasks_fragment, null);

        mTaskListRecyclerView = view.findViewById(R.id.tasklist_recyclerview);
        mAddTaskButton = view.findViewById(R.id.add_task_button);
        mTaskList = new ArrayList<>();
        dbHelper = new TaskDBHelper(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},1);
        }

        //Getting task data from screen..
        try{
            getTaskData();
        }catch (Exception e){

        }

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });

        return view;
    }

    private void createTask() {
        mDialogBox = new TaskDialogBox(getActivity(), this);
        openTaskDialog();
    }

    private void openTaskDialog() {
        Window window = mDialogBox.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialogBox.show();
    }

    private void getTaskData() {
        mTaskList = dbHelper.getData();
        if (!mTaskList.isEmpty()) {

            Collections.sort(mTaskList,Collections.reverseOrder(new Comparator<TaskModel>() {
                @Override
                public int compare(TaskModel taskModel, TaskModel t1) {
                    return taskModel.getDate().compareTo(t1.getDate());
                }
            }));


            mTaskListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            mTaskListAdapter = new TaskListAdapter(getActivity(), mTaskList,this);
            mTaskListRecyclerView.setAdapter(mTaskListAdapter);
        }
    }

    @Override
    public void onEditClick(TaskModel taskModel) {
        mDialogBox = new TaskDialogBox(getActivity(),taskModel, this);
        openTaskDialog();
    }

    @Override
    public void onDeleteClick(TaskModel taskModel) {
        dbHelper.deleteTaskData(taskModel.getTitle());
        try {
            getTaskData();
        }
        catch (Exception e){

        }
    }

    @Override
    public void onTaskDismiss() {
        try {
            getTaskData();
        }
        catch (Exception e){

        }
    }
}
