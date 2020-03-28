package com.example.libraapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.libraapplication.Model.TaskModel;
import com.example.libraapplication.Model.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TaskDialogBox extends Dialog implements Utility.GetUserModelListListener {
    private Context mContext;
    private EditText edit_task_title;
    private TextView edit_add_date, mselectedTeamText;
    private EditText edit_task_desc;
    private TextView edit_assignto;
    private EditText edit_task_note;
    private Button button_save;
    private Calendar mCalender;
    private TaskModel taskModel;
    private boolean isFromEdit;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private boolean[] checkedItems;
    private ArrayList<Integer> mSelectedTeamList = new ArrayList<>();
    private String[] userNameList;
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<UsersModel> usersModelArrayList;
    private ArrayList<String> euidList = new ArrayList<>();

    public TaskDialogBox(@NonNull Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
    }

    public TaskDialogBox(@NonNull Context context, TaskModel taskModel) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
        this.taskModel = taskModel;
        isFromEdit = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_dialog);
        mCalender = Calendar.getInstance();

        edit_task_title = findViewById(R.id.edit_task_title);
        edit_add_date = findViewById(R.id.edit_task_date);
        edit_task_desc = findViewById(R.id.edit_task_desc);
        edit_assignto = findViewById(R.id.edit_task_assignto);
        edit_task_note = findViewById(R.id.edit_add_task_note);
        button_save = findViewById(R.id.button_save);
        mselectedTeamText = findViewById(R.id.selectedTeamText);

        // Code to get all users....
        Utility utility = new Utility(this);
        utility.getAllUserModelList();

        if (taskModel != null) {
            edit_task_title.setText(taskModel.getTitle());
            edit_add_date.setText(taskModel.getDate());
            edit_task_desc.setText(taskModel.getDesc());
            mselectedTeamText.setText(taskModel.getAssignto());
            edit_task_note.setText(taskModel.getNote());
        }

        edit_assignto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createMultiChoiceDialog();

                }catch (Exception e){
                    Toast.makeText(mContext, "Wait..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseRef = mDatabase.getReference();


                if (checkValidation()) {
                    Toast.makeText(mContext, "Please fill form completely..", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (isFromEdit){

                        // Code to set data to all team members..............
                        if (usersModelArrayList.size() > 0){
                            for (String euid : euidList){
                                if (euid != FirebaseAuth.getInstance().getUid()){
                                    mDatabaseRef.child(euid)
                                            .child("Tasks").child(taskModel.getTitle()).setValue(taskModel);
                                }
                            }

                        }

                        mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                                .child("Tasks").child(taskModel.getTitle()).removeValue();
                    }
                    else {
                        taskModel = new TaskModel();
                    }
                    taskModel.setTitle(edit_task_title.getText().toString());
                    taskModel.setAssignto(mselectedTeamText.getText().toString());
                    taskModel.setDesc(edit_task_desc.getText().toString());
                    taskModel.setDate(edit_add_date.getText().toString());
                    taskModel.setNote(edit_task_note.getText().toString());

                    // Code to set data to all team members..............
                    if (usersModelArrayList.size() > 0){
                        for (String euid : euidList){
                            if (euid != FirebaseAuth.getInstance().getUid()){
                                mDatabaseRef.child(euid)
                                        .child("Tasks").child(taskModel.getTitle()).setValue(taskModel);
                            }
                        }

                    }

                    mDatabaseRef.child(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                            .child("Tasks").child(taskModel.getTitle()).setValue(taskModel);
                    clearEditBoxes();
                    dismiss();
                    taskModel = null;
                }
            }
        });


        DatePickerDialog.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {
            mCalender.set(Calendar.YEAR, year);
            mCalender.set(Calendar.MONTH, month);
            mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setDateEditText(edit_add_date, mCalender);
        };

        edit_add_date.setOnClickListener(v -> new DatePickerDialog(Objects.requireNonNull(mContext),
                date, mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)).show());

    }

    private void createMultiChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Please select your team");
        builder.setMultiChoiceItems(userNameList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked){
                    if (!mSelectedTeamList.contains(position)){
                        mSelectedTeamList.add(position);
                        euidList.add(usersModelArrayList.get(position).getEuid());
                    }
                    else {
                        for (int i = 0; i < mSelectedTeamList.size(); i++) {
                            if (mSelectedTeamList.get(i) == position) {
                                mSelectedTeamList.remove(i);
                            }
                        }
                        euidList.remove(usersModelArrayList.get(position).getEuid());
                    }
                }
                else{

                    for (int i = 0; i < mSelectedTeamList.size(); i++) {
                        if (mSelectedTeamList.get(i) == position) {
                            mSelectedTeamList.remove(i);
                        }
                    }
                    euidList.remove(usersModelArrayList.get(position).getEuid());
                }
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i< mSelectedTeamList.size(); i++){
                    item = item + userNameList[mSelectedTeamList.get(i)];
                    if (i != mSelectedTeamList.size() - 1){
                        item = item + ", ";
                    }
                }
                if (mSelectedTeamList.size() > 0){
                    mselectedTeamText.setVisibility(View.VISIBLE);
                }
                else {
                    mselectedTeamText.setVisibility(View.GONE);
                }
                mselectedTeamText.setText(item);
            }
        });

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mSelectedTeamList.size() > 0){
                    mselectedTeamText.setVisibility(View.VISIBLE);
                }
                else {
                    mselectedTeamText.setVisibility(View.GONE);
                }
                dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i=0; i < checkedItems.length; i++){
                    checkedItems[i] = false;
                    mSelectedTeamList.clear();
                    euidList.clear();
                    mselectedTeamText.setText("");
                    mselectedTeamText.setVisibility(View.GONE);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String[] getStringArray(ArrayList<String> arr)
    {

        String str[] = new String[arr.size()];

        for (int j = 0; j < arr.size(); j++) {

            str[j] = arr.get(j);
        }

        return str;
    }


    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        case_date.setText(sdf.format(mCalender.getTime()));
    }

    private boolean checkValidation() {
        if (edit_task_title.getText().toString().isEmpty() || edit_add_date.getText().toString().isEmpty() ||
                mselectedTeamText.getText().toString().isEmpty() || edit_task_desc.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clearEditBoxes() {
        edit_task_title.setText("");
        edit_add_date.setText("");
        edit_task_desc.setText("");
        mselectedTeamText.setText("");
    }


    @Override
    public void getUserList(ArrayList<UsersModel> usersModelArrayList) {
        setTeamListAdapter(usersModelArrayList);
    }

    void setTeamListAdapter(ArrayList<UsersModel> usersModelArrayList) {
        Toast.makeText(mContext, "Data Recieved", Toast.LENGTH_SHORT).show();
        for (UsersModel usersModel : usersModelArrayList) {
            users.add(usersModel.getUname());
        }
        this.usersModelArrayList = usersModelArrayList;
        userNameList = getStringArray(users);
        checkedItems = new boolean[users.size()];
    }
}
