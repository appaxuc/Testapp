package com.example.app.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app.testapp.database.PersonBaseHelper;
import com.example.app.testapp.database.PersonCursorWrapper;
import com.example.app.testapp.database.PersonDbSchema;
import com.example.app.testapp.database.PersonDbSchema.PersonTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonBank {

    static final String LOG_TAG = "myLogs";

    private static PersonBank sPersonBank;

    private static SQLiteDatabase mDatabase;

    public static PersonBank get(Context context) {
        Log.d(LOG_TAG, "Call GET");
        if (sPersonBank == null) {
            Log.d(LOG_TAG, "Update DB");
            sPersonBank = new PersonBank(context);
        }
        return sPersonBank;
    }
    private PersonBank(Context context) {
        Log.d(LOG_TAG, "Call DB create");

        mDatabase = new PersonBaseHelper(context)
                .getWritableDatabase();
        mDatabase.delete("persons", null, null);
        Log.d(LOG_TAG, "DB is created");
    }

    public static void addPerson(List<Person> p) {
            getContentValues(p);
    }

    public List<Person> getSpecPerson() {
        List<Person> persons = new ArrayList<>();
        PersonCursorWrapper cursor = queryIdPerson(null, null);

        //int specColumnIndex = cursor.getColumnIndex(PersonTable.Cols.SPEC);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                persons.add(cursor.getSpecPerson());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        Log.d(LOG_TAG, "Размер выборки " + String.valueOf(persons.size()));
        return persons;
    }

    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();

        PersonCursorWrapper cursor = queryPerson(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                persons.add(cursor.getPerson());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return persons;
    }

    public List<Person> getPersonFromSpec(int specId) {
        List<Person> persons = new ArrayList<>();

        Log.d(LOG_TAG, "Call getPersonFromSpec()");
        PersonCursorWrapper cursorWrapper = querySpecPerson("spec_id = " + specId,
                null);

        Log.d(LOG_TAG, "Value of cw: " + String.valueOf(cursorWrapper.getCount()));

        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                persons.add(cursorWrapper.getPerson());
                cursorWrapper.moveToNext();
            }
            Log.d(LOG_TAG, "move to CW.getPersonFromSpec()");
        } finally {
            cursorWrapper.close();
        }
        Log.d(LOG_TAG, String.valueOf(persons.size()));
        return persons;
    }

    public Person getPerson(UUID uuid) {
        Log.d(LOG_TAG, "Call getPerson()");
        PersonCursorWrapper cursorWrapper = queryPerson(
                PersonTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() }
        );

        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }

            cursorWrapper.moveToFirst();
            Log.d(LOG_TAG, "move to CW.getPerson()");
            return cursorWrapper.getPerson();
        } finally {
            cursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(List<Person> person) {
        Log.d(LOG_TAG, "start getContentValues");
        ContentValues values = new ContentValues();
        Log.d(LOG_TAG, "CV is created");
        for (int i = 0; i < person.size(); i++) {
            values.put(PersonTable.Cols.UUID, person.get(i).getUUID().toString());
            values.put(PersonTable.Cols.SPEC_ID, person.get(i).getSpecId());
            values.put(PersonTable.Cols.FIRST_NAME, person.get(i).getFirstName());
            values.put(PersonTable.Cols.LAST_NAME, person.get(i).getLastName());
            values.put(PersonTable.Cols.BIRTH, person.get(i).getBirth());
            values.put(PersonTable.Cols.AGE, person.get(i).getAge());
            values.put(PersonTable.Cols.SPEC, person.get(i).getSpec());
            mDatabase.insert(PersonTable.NAME, null, values);
            Log.d(LOG_TAG, "added person " + i);
        }

        Log.d(LOG_TAG,"DB is full");

        return values;
    }

    private PersonCursorWrapper queryPerson (String whereSel, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PersonTable.NAME,
                null,
                whereSel,
                whereArgs,
                null,
                null,
                null
        );
        return new PersonCursorWrapper(cursor);
    }

    private PersonCursorWrapper queryIdPerson (String whereSel, String[] whereArgs) {
        String[] strId = {PersonTable.Cols.SPEC_ID, PersonTable.Cols.SPEC};
        Cursor cursor = mDatabase.query(
                true,
                PersonTable.NAME,
                strId,
                whereSel,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new PersonCursorWrapper(cursor);
    }

    private PersonCursorWrapper querySpecPerson(String whereSel, String[] whereArgs) {

        Cursor cursor = mDatabase.query(
                "persons",
                null,
                whereSel,
                whereArgs,
                null,
                null,
                null
        );
        Log.d(LOG_TAG, "Cursor после qSP  " + cursor.getCount());
        return new PersonCursorWrapper(cursor);
    }
}