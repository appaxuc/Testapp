package com.example.app.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.testapp.database.PersonBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonBank {

    private static PersonBank sPersonBank;

    private List<Person> mPerson;
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
        mPerson = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setTitle("Person #" + i);
            mPerson.add(person);
        }
    }

    public List<Person> getPerson() {
        return mPerson;
    }

    public Person getPerson(UUID uuid) {
        for (Person person : mPerson) {
            if (person.getUUID().equals(uuid)) {
                return person;
            }
        }
        return null;
    }
}
