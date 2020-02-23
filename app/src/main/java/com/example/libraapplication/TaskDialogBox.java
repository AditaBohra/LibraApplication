package com.example.libraapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.libraapplication.Database.TaskDBHelper;
import com.example.libraapplication.Model.TaskModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TaskDialogBox extends Dialog {
    private Context mContext;
    private EditText edit_task_title;
    private TextView edit_add_date;
    private EditText edit_task_desc;
    private EditText edit_assignto;
    private EditText edit_task_note;
    private Button button_save;
    private TaskDBHelper dbHelper;
    private Calendar mCalender;
    private TaskDismissListener taskDismissListener;
    private TaskModel taskModel;
    private boolean isFromEdit;

    public interface TaskDismissListener{
        void onTaskDismiss();
    }
    public TaskDialogBox(@NonNull Context context, TaskDismissListener taskDismissListener) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
        this.taskDismissListener = taskDismissListener;
    }

    public TaskDialogBox(@NonNull Context context, TaskModel taskModel, TaskDismissListener taskDismissListener) {
        super(context,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        mContext = context;
        this.taskModel = taskModel;
        this.taskDismissListener = taskDismissListener;
        isFromEdit = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_dialog);
        mCalender = Calendar.getInstance();
        dbHelper = new TaskDBHelper(mContext);
        dbHelper.getReadableDatabase();

        edit_task_title = findViewById(R.id.edit_task_title);
        edit_add_date = findViewById(R.id.edit_task_date);
        edit_task_desc = findViewById(R.id.edit_task_desc);
        edit_assignto = findViewById(R.id.edit_task_assignto);
        edit_task_note = findViewById(R.id.edit_add_task_note);
        button_save = findViewById(R.id.button_save);

        if (taskModel != null) {
            edit_task_title.setText(taskModel.getTitle());
            edit_add_date.setText(taskModel.getDate());
            edit_task_desc.setText(taskModel.getDesc());
            edit_assignto.setText(taskModel.getAssignto());
            edit_task_note.setText(taskModel.getNote());
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {
                    Toast.makeText(mContext, "Please fill form completely..", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (isFromEdit){
                        dbHelper.deleteTaskData(taskModel.getTitle());
                    }
                    dbHelper.saveData(edit_task_title.getText().toString(), edit_task_desc.getText().toString(),
                            edit_add_date.getText().toString(), edit_assignto.getText().toString());
                    clearEditBoxes();
                    dismiss();
                    taskDismissListener.onTaskDismiss();
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


//        mCalenderBox.setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                SharedPreferences sharedPreferences = mContext.getSharedPreferences("datestorage", Context.MODE_PRIVATE);
//                calenderDate = sharedPreferences.getString("date", null);
//                edit_add_date.setText(calenderDate);
//            }
//        });
    }

    private void setDateEditText(TextView case_date, Calendar mCalender) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        case_date.setText(sdf.format(mCalender.getTime()));
    }

    private boolean checkValidation() {
        if (edit_task_title.getText().toString().isEmpty() || edit_add_date.getText().toString().isEmpty() ||
                edit_assignto.getText().toString().isEmpty() || edit_task_desc.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clearEditBoxes() {
        edit_task_title.setText("");
        edit_add_date.setText("");
        edit_task_desc.setText("");
        edit_assignto.setText("");
    }


}
