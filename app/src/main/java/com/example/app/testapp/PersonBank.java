package com.example.app.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app.testapp.database.PersonBaseHelper;
import com.example.app.testapp.database.PersonCursorWrapper;
import com.example.app.testapp.database.PersonDbSchema.PersonTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonBank {

    private static final String LOG_TAG = "myLogs";

    private static PersonBank sPersonBank;

    private static SQLiteDatabase mDatabase;

    public static PersonBank get(Context context) {
        Log.d(LOG_TAG, "Call GET");
        if (sPersonBank == null) {
            Log.d(LOG_TAG, "Update DB");
            sPersonBank = new PersonBank(context);
        }
        return sPersonBank;
    }                                             // Проверка существования БД

    private PersonBank(Context context) {
        Log.d(LOG_TAG, "Call DB create");

        mDatabase = new PersonBaseHelper(context)
                .getWritableDatabase();
        mDatabase.delete("persons", null, null);
    }                                                       // Создание БД

    public static void addPerson(List<Person> p) {
            getContentValues(p);
    }

    public List<Person> getSpecPerson() {
        List<Person> persons = new ArrayList<>();
        PersonCursorWrapper cursor = queryIdPerson(null, null);

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
    }                                                       // Запрос выборки специализаций для 1го экрана

    public List<Person> getPersonFromSpec(int specId) {
        List<Person> persons = new ArrayList<>();

        Log.d(LOG_TAG, "Call getPersonFromSpec()");
        PersonCursorWrapper cursorWrapper = queryPerson("spec_id = " + specId,
                null);

        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                persons.add(cursorWrapper.getPerson());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return persons;
    }                                         // Запрос выборки по специализации для 2го экрана

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
            return cursorWrapper.getPerson();
        } finally {
            cursorWrapper.close();
        }
    }                                                        // Получение отдельного Person

    private static ContentValues getContentValues(List<Person> person) {
        ContentValues values = new ContentValues();

        for (int i = 0; i < person.size(); i++) {
            values.put(PersonTable.Cols.UUID, person.get(i).getUUID().toString());
            values.put(PersonTable.Cols.SPEC_ID, person.get(i).getSpecId());
            values.put(PersonTable.Cols.FIRST_NAME, person.get(i).getFirstName());
            values.put(PersonTable.Cols.LAST_NAME, person.get(i).getLastName());
            values.put(PersonTable.Cols.BIRTH, person.get(i).getBirth());
            values.put(PersonTable.Cols.AGE, person.get(i).getAge());
            values.put(PersonTable.Cols.SPEC, person.get(i).getSpec());
            mDatabase.insert(PersonTable.NAME, null, values);
        }
        return values;
    }                        // Заполнение БД

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
    }             // Выборка из БД

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
    }           // Выборка из БД с условием
}