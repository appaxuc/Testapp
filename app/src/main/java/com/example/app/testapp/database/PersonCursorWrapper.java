package com.example.app.testapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.app.testapp.Person;

import java.util.Date;
import java.util.UUID;

public class PersonCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public PersonCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Person getPerson() {
        String uuidString = getString(getColumnIndex(PersonDbSchema.PersonTable.Cols.UUID));
        String id = getString(getColumnIndex(PersonDbSchema.PersonTable.Cols.UUID));
        String firstName = getString(getColumnIndex(PersonDbSchema.PersonTable.Cols.FIRST_NAME));
        String lastName = getString(getColumnIndex(PersonDbSchema.PersonTable.Cols.LAST_NAME));
        Long birth = getLong(getColumnIndex(PersonDbSchema.PersonTable.Cols.BIRTH));
        String spec = getString(getColumnIndex(PersonDbSchema.PersonTable.Cols.SPEC));

        Person person = new Person(UUID.fromString(uuidString));
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDate(new Date(birth));
        person.setSpec(spec);

        return person;
    }
}
