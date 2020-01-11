package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Database.TaskDBHelper;
import com.example.libraapplication.R;
import com.example.libraapplication.TaskDialogBox;
import com.example.libraapplication.Adapter.TaskListAdapter;
import com.example.libraapplication.Model.TaskModel;

import java.util.ArrayList;

public class TasksFragment extends Fragment {
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
        mDialogBox = new TaskDialogBox(getActivity());

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
                mDialogBox.show();
            }
        });

        mDialogBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    getTaskData();
                }
                catch (Exception e){

                }
            }
        });

        return view;
    }

    private void getTaskData() {
        mTaskList = dbHelper.getData();
        if (!mTaskList.isEmpty()) {
            mTaskListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            mTaskListAdapter = new TaskListAdapter(getActivity(), mTaskList);
            mTaskListRecyclerView.setAdapter(mTaskListAdapter);
        }
    }
}
