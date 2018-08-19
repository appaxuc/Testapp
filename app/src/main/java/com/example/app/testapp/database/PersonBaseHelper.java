package com.example.app.testapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.testapp.database.PersonDbSchema.PersonTable;

public class PersonBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION =1;
    private static final String DATABASE_NAME = "personBase.db";

    public PersonBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PersonTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PersonTable.Cols.UUID + ", " +
                PersonTable.Cols.SPEC_ID + ", " +
                PersonTable.Cols.FIRST_NAME + ", " +
                PersonTable.Cols.LAST_NAME + ", " +
                PersonTable.Cols.BIRTH + ", " +
                PersonTable.Cols.AGE + ", " +
                PersonTable.Cols.SPEC + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
