package com.example.libraapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.libraapplication.Model.TaskModel;

import java.util.ArrayList;

public class TaskDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "task_data.sqlite";
    private static String tablename = "task";

    public TaskDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+tablename+" (title TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String querry = "DROP TABLE IF EXISTS "+tablename;
        sqLiteDatabase.execSQL(querry);
        onCreate(sqLiteDatabase);
    }

    public void saveData(String title, String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(tablename,null,contentValues);
    }

    public ArrayList<TaskModel> getData()
    {
        String querry = "SELECT * FROM "+tablename;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(querry,null);

        ArrayList<TaskModel> arrayList = new ArrayList<>();

        if (cursor != null)
        {
            cursor.moveToFirst();
            do{
                TaskModel taskModel = new TaskModel();
                taskModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                taskModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
                arrayList.add(taskModel);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return arrayList;
    }
}
