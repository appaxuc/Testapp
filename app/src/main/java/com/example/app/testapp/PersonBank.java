package com.example.app.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.testapp.database.PersonBaseHelper;
import com.example.app.testapp.database.PersonDbSchema.PersonTable;
import com.example.app.testapp.httpCon.PersonItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonBank {

    private static PersonBank sPersonBank;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PersonBank get(Context context) {
        if (sPersonBank == null) {
            sPersonBank = new PersonBank(context);
        }
        return sPersonBank;
    }
    private PersonBank(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PersonBaseHelper(mContext)
                .getWritableDatabase();
        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setTitle("Person #" + i);
        }
    }

    public void addPerson (PersonItem person) {
        ContentValues values = getContentValues(person);

        mDatabase.insert(PersonTable.NAME, null, values);
    }

    public List<Person> getPerson() {
        return new ArrayList<>();
    }

    public Person getPerson(UUID uuid) {
        return null;
    }

    private static ContentValues getContentValues(PersonItem person) {
        ContentValues values = new ContentValues();
        values.put(PersonTable.Cols.ID, person.getId());
        values.put(PersonTable.Cols.FIRST_NAME, person.getFirstName());
        values.put(PersonTable.Cols.LAST_NAME, person.getLastName());
        values.put(PersonTable.Cols.BIRTH, person.getBirth());
        values.put(PersonTable.Cols.SPEC, person.getSpec());
        return values;
    }
}
