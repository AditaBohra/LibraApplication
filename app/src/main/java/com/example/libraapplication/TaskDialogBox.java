package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.libraapplication.Database.TaskDBHelper;

public class TaskDialogBox extends Dialog
{
    private Context mContext;
    private EditText edit_task_title;
    private TextView button_save;
    private TextView add_date_text;
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
        button_save = findViewById(R.id.button_save);
        add_date_text = findViewById(R.id.add_date_text);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.saveData(edit_task_title.getText().toString(),calenderDate);
                dismiss();
            }
        });

        add_date_text.setOnClickListener(new View.OnClickListener() {
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
                add_date_text.setText(calenderDate);
            }
        });
    }


}
