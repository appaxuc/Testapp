package com.example.app.testapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.app.testapp.Person;

import java.util.UUID;

import static com.example.app.testapp.database.PersonDbSchema.PersonTable.*;

public class PersonCursorWrapper extends CursorWrapper {

    public PersonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Person getPerson() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        int specId = getInt(getColumnIndex(Cols.SPEC_ID));
        String firstName = getString(getColumnIndex(Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(Cols.LAST_NAME));
        String birth = getString(getColumnIndex(Cols.BIRTH));
        int age = getInt(getColumnIndex(Cols.AGE));
        String spec = getString(getColumnIndex(Cols.SPEC));

        Person person = new Person(UUID.fromString(uuidString));
        person.setSpecId(specId);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirth(birth);
        person.setAge(age);
        person.setSpec(spec);

        return person;
    }

    public Person getSpecPerson() {
        Person person = new Person(UUID.randomUUID());
        //String uuidString = getString(getColumnIndex(Cols.UUID));
        int specId = getInt(getColumnIndex(Cols.SPEC_ID));
        //String firstName = getString(getColumnIndex(Cols.FIRST_NAME));
        //String lastName = getString(getColumnIndex(Cols.LAST_NAME));
        //String birth = getString(getColumnIndex(Cols.BIRTH));
        String spec = getString(getColumnIndex(Cols.SPEC));

        //Person person = new Person(UUID.fromString(uuidString));
        person.setSpecId(specId);
        //person.setFirstName(firstName);
        //person.setLastName(lastName);
        //person.setBirth(birth);
        person.setSpec(spec);

        return person;
    }
}
