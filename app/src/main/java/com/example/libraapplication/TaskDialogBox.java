package com.example.libraapplication;

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
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.libraapplication.Database.TaskDBHelper;

public class TaskDialogBox extends Dialog
{
    private Context mContext;
    private EditText edit_task_title;
    private EditText edit_add_date;
    private EditText edit_task_desc;
    private EditText edit_assignto;

    private Button button_save;
    private TaskDBHelper dbHelper;
    private CalenderBox mCalenderBox;
    private String calenderDate;

    public TaskDialogBox(@NonNull Context context) {
        super(context);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_dialog);
        mCalenderBox = new CalenderBox(mContext);
        dbHelper = new TaskDBHelper(mContext);
        dbHelper.getReadableDatabase();

        edit_task_title = findViewById(R.id.edit_task_title);
        edit_add_date = findViewById(R.id.edit_task_date);
        edit_task_desc = findViewById(R.id.edit_task_desc);
        edit_assignto = findViewById(R.id.edit_task_assignto);
        button_save = findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    Toast.makeText(mContext, "Please fill form completely..", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    dbHelper.saveData(edit_task_title.getText().toString(),edit_task_desc.getText().toString(),
                            calenderDate,edit_assignto.getText().toString());
                    clearEditBoxes();
                    dismiss();
                }


            }
        });


        edit_add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalenderBox.show();
                Window window = mCalenderBox.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            }
        });

        mCalenderBox.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("datestorage",Context.MODE_PRIVATE);
                calenderDate = sharedPreferences.getString("date",null);
                edit_add_date.setText(calenderDate);
            }
        });
    }

    private boolean checkValidation() {
        if (edit_task_title.getText().toString().isEmpty() || edit_add_date.getText().toString().isEmpty() ||
        edit_assignto.getText().toString().isEmpty() || edit_task_desc.getText().toString().isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    public void clearEditBoxes(){
        edit_task_title.setText("");
        edit_add_date.setText("");
        edit_task_desc.setText("");
        edit_assignto.setText("");
    }


}
