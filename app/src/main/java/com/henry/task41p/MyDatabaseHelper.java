package com.henry.task41p;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "TaskLibrary.db";
    public static final int DATABASE_VERSION = 2;

    String TABLE_NAME = "my_task";
    String COLUMN_ID = "id";
    String COLUMN_TITLE = "task_title";
    String COLUMN_DESCRIPTION = "task_description";
    String COLUMN_NUMBER = "task_number";
    String COLUMN_DATE = "task_due_date";
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
            "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    //add task
    public void addTask(String title, String unit, String description, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NUMBER, unit);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, dueDate);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //Update function
    void updateData(String row_id, String title, String unit, String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_NUMBER, unit);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, date);

        long result = db.update(TABLE_NAME, cv, " id=?", new String[]{row_id});

        if(result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Sucessfully Update", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete function
    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});

        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Delete", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
