package com.example.libraapplication.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libraapplication.Adapter.TaskListAdapter;
import com.example.libraapplication.Model.TaskModel;
import com.example.libraapplication.R;
import com.example.libraapplication.TaskDialogBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class TasksFragment extends Fragment implements TaskListAdapter.TaskClickListener{
    private TaskListAdapter mTaskListAdapter;
    private RecyclerView mTaskListRecyclerView;
    private Button mAddTaskButton;
    private ArrayList<TaskModel> mTaskList;
    private TaskDialogBox mDialogBox;
    private FirebaseDatabase mDatabase;
    public static final String TAG = TasksFragment.class.getSimpleName();
    private TaskModel taskModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tasks_fragment, null);

        mTaskListRecyclerView = view.findViewById(R.id.tasklist_recyclerview);
        mAddTaskButton = view.findViewById(R.id.add_task_button);
        mTaskList = new ArrayList<>();


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},1);
        }

        //Getting task data from screen..
            getTaskData();

        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });

        return view;
    }

    private void createTask() {
        mDialogBox = new TaskDialogBox(getActivity());
        openTaskDialog();
    }

    private void openTaskDialog() {
        Window window = mDialogBox.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mDialogBox.show();
    }

    private void getTaskData() {
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Tasks");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                   mTaskList.clear();
                                                   for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                                       taskModel = dataSnapshot1.getValue(TaskModel.class);
                                                       mTaskList.add(taskModel);

                                                   }
                                                   if (!mTaskList.isEmpty()) {

                                                       Collections.sort(mTaskList,Collections.reverseOrder(new Comparator<TaskModel>() {
                                                           @Override
                                                           public int compare(TaskModel taskModel, TaskModel t1) {
                                                               return taskModel.getDate().compareTo(t1.getDate());
                                                           }
                                                       }));


                                                       mTaskListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                                       mTaskListAdapter = new TaskListAdapter(getActivity(), mTaskList,TasksFragment.this);
                                                       mTaskListRecyclerView.setAdapter(mTaskListAdapter);
                                                   }
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError databaseError) {
                                                   Log.d(TAG, databaseError.getMessage());
                                               }
                                           });


    }

    @Override
    public void onEditClick(TaskModel taskModel) {
        mDialogBox = new TaskDialogBox(getActivity(),taskModel);
        openTaskDialog();
    }

    @Override
    public void onDeleteClick(TaskModel taskModel) {
        mDatabase.getReference()
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Tasks").child(taskModel.getTitle()).removeValue();
        mTaskList.remove(taskModel);
       mTaskListAdapter.notifyDataSetChanged();

    }

}
