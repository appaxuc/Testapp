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

    private List<Person> mPersonList;

    private Context mContext;
    private static SQLiteDatabase mDatabase;

    public static PersonBank get(Context context) {
        if (sPersonBank == null) {
            sPersonBank = new PersonBank(context);
        }
        return sPersonBank;
    }
    private PersonBank(Context context) {
        mPersonList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Person person = new Person();
            person.setTitle("Person #" + i);
            mPersonList.add(person);
        }

        mContext = context.getApplicationContext();
        mDatabase = new PersonBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static void addPerson(List<PersonItem> p) {
            ContentValues values = getContentValues(p);
            //System.out.println(values);
            //mDatabase.insert(PersonTable.NAME, null, values);
    }

    public List<Person> getPersons() {
        return mPersonList;
    }

    public Person getPerson(UUID id) {
        for (Person person : mPersonList) {
            if (person.getUUID().equals(id)) {
                return person;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(List<PersonItem> person) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < person.size(); i++) {
            values.put(PersonTable.Cols.UUID, person.get(i).getId());
            values.put(PersonTable.Cols.FIRST_NAME, person.get(i).getFirstName());
            values.put(PersonTable.Cols.LAST_NAME, person.get(i).getLastName());
            values.put(PersonTable.Cols.BIRTH, person.get(i).getBirth());
            values.put(PersonTable.Cols.SPEC, person.get(i).getSpec());
            mDatabase.insert(PersonTable.NAME, null, values);
        }
        //System.out.println(values);

        return values;
    }
}
