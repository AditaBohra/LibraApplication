package com.example.libraapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TaskDialogBox extends Dialog
{
    private Context mContext;
    private EditText edit_task_title;
    private TextView button_save;
    private DBHelper dbHelper;

    public TaskDialogBox(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.task_dialog);
        dbHelper = new DBHelper(mContext);
        dbHelper.getReadableDatabase();

        edit_task_title = findViewById(R.id.edit_task_title);
        button_save = findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.saveData(edit_task_title.getText().toString(),"date");
                dismiss();
            }
        });
    }

}
